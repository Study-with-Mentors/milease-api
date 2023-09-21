package vn.id.milease.mileaseapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.id.milease.mileaseapi.model.dto.StepDto;
import vn.id.milease.mileaseapi.model.dto.create.CreateStepDto;
import vn.id.milease.mileaseapi.model.dto.update.UpdateStepDto;
import vn.id.milease.mileaseapi.model.entity.place.Place;
import vn.id.milease.mileaseapi.model.entity.plan.Plan;
import vn.id.milease.mileaseapi.model.entity.step.Step;
import vn.id.milease.mileaseapi.model.exception.ArgumentsException;
import vn.id.milease.mileaseapi.model.exception.ConflictException;
import vn.id.milease.mileaseapi.model.exception.NotFoundException;
import vn.id.milease.mileaseapi.repository.PlanRepository;
import vn.id.milease.mileaseapi.repository.StepRepository;
import vn.id.milease.mileaseapi.service.PlaceService;
import vn.id.milease.mileaseapi.service.PlanService;
import vn.id.milease.mileaseapi.service.StepService;
import vn.id.milease.mileaseapi.util.ApplicationMapper;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class StepServiceImpl implements StepService {
    private final StepRepository stepRepository;
    private final PlanRepository planRepository;
    private final PlanService planService;
    private final PlaceService placeService;
    private final ApplicationMapper mapper;

    @Override
    public StepDto getStepById(long id) {
        return mapper.getStepMapper().toDto(getStep(id));
    }

    @Override
    public List<StepDto> getStepByPlanId(long planId) {
        Plan plan = planService.getPlan(planId);
        planService.checkCurrentUserPermission(plan);
        List<Step> steps = plan.getSteps();
        return steps.stream()
                .map(mapper.getStepMapper()::toDto)
                .toList();
    }

    @Override
    public void swapStep(long step1Id, long step2Id) {
        if (step1Id == step2Id) {
            throw new ConflictException("Cannot swap the same step");
        }
        Step step1 = getStep(step1Id);
        Step step2 = getStep(step2Id);
        if (!step1.getPlan().getId().equals(step2.getPlan().getId())) {
            throw new ArgumentsException("Two steps do not belong to a plan");
        }
        planService.checkCurrentUserPermission(step1.getPlan());

        linkStep(step1, step2.getPreviousStep(), step2.getNextStep());
        linkStep(step2, step1.getPreviousStep(), step1.getNextStep());
        // TODO: bug when step1 and step2 is adjacent
        stepRepository.save(step1);
        stepRepository.save(step2);
    }

    private static void linkStep(Step step, Step prevStep, Step nextStep) {
        step.setPreviousStep(prevStep);
        if (prevStep != null) {
            prevStep.setNextStep(step);
        }
        step.setNextStep(nextStep);
        if (nextStep != null) {
            nextStep.setPreviousStep(step);
        }
    }

    @Override
    public StepDto addStep(CreateStepDto dto) {
        Plan plan = planService.getPlan(dto.getPlanId());
        planService.checkCurrentUserPermission(plan);
        Place place = placeService.getPlace(dto.getPlaceId());

        Step step = mapper.getStepMapper().toEntity(dto);
        step.setPlan(plan);
        step.setPlace(place);
        step.setId(0L);

        if (dto.getPrevStepId() == null) {
            // insert to the head of plan
            Step currentHead = plan.getFirstStep();
            plan.setFirstStep(step);
            if (currentHead != null) {
                step.setNextStep(currentHead);
                currentHead.setPreviousStep(step);
            }
        } else {
            if (plan.getFirstStep() == null) {
                // insert the first step in plan
                plan.setFirstStep(step);
            } else {
                // insert step in the middle of linked list
                Step prevStep = getStep(dto.getPrevStepId());
                Step nextStep = prevStep.getNextStep();

                prevStep.setNextStep(step);
                step.setPreviousStep(prevStep);

                if (nextStep != null) {
                    step.setNextStep(nextStep);
                    nextStep.setPreviousStep(step);
                }
            }
        }


        planRepository.save(plan);
        step = stepRepository.save(step);
        return mapper.getStepMapper().toDto(step);
    }

    @Override
    public StepDto updateStep(UpdateStepDto dto) {
        Step step = getStep(dto.getId());
        planService.checkCurrentUserPermission(step.getPlan());
        mapper.getStepMapper().toEntity(dto, step);

        step = stepRepository.save(step);
        return mapper.getStepMapper().toDto(step);
    }

    @Override
    public void deleteStep(long id) {
        Step step = getStep(id);
        planService.checkCurrentUserPermission(step.getPlan());
        if (step.getPreviousStep() == null) {
            // Update the head of linked list
            Plan plan = step.getPlan();
            plan.setFirstStep(null);
            planRepository.save(plan);
        } else {
            // Update the next/prev link
            Step prevStep = step.getPreviousStep();
            Step nextStep = step.getNextStep();
            prevStep.setNextStep(nextStep);
            nextStep.setPreviousStep(prevStep);

            stepRepository.save(nextStep);
            stepRepository.save(prevStep);
        }
        stepRepository.deleteById(id);
    }

    private Step getStep(long id) {
        return stepRepository.findById(id).orElseThrow(() -> new NotFoundException(Step.class, id));
    }
}
