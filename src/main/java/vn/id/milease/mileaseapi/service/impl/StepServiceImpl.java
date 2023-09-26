package vn.id.milease.mileaseapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.id.milease.mileaseapi.model.dto.StepDto;
import vn.id.milease.mileaseapi.model.dto.create.CreateStepDto;
import vn.id.milease.mileaseapi.model.dto.update.UpdateStepDto;
import vn.id.milease.mileaseapi.model.entity.place.Place;
import vn.id.milease.mileaseapi.model.entity.plan.Plan;
import vn.id.milease.mileaseapi.model.entity.plan.PlanIdOnly;
import vn.id.milease.mileaseapi.model.entity.step.Step;
import vn.id.milease.mileaseapi.model.entity.step.StepIdOnly;
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

    private void linkStep(StepIdOnly step, Long prevStepId, Long nextStepId) {
        linkPreviousStep(step, prevStepId);
        linkNextStep(step, nextStepId);
    }

    private void linkPreviousStep(StepIdOnly step, Long previousStepId) {
        step.setPreviousStepId(previousStepId);
        if (previousStepId != null) {
            stepRepository.updateNextStepById(previousStepId, step.getId());
        }
    }

    private void linkNextStep(StepIdOnly step, Long nextStepId) {
        step.setNextStepId(nextStepId);
        if (nextStepId != null) {
            stepRepository.updatePreviousStepById(nextStepId, step.getId());
        }
    }

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
        // TODO [Duy, P2]: test adjacent node, 1 node in between, 2 node in between, null head, null tail,...
        if (step1Id == step2Id) {
            throw new ConflictException("Cannot swap the same step");
        }
        StepIdOnly step1 = getStepIdOnly(step1Id);
        StepIdOnly step2 = getStepIdOnly(step2Id);
        if (!step1.getPlanId().equals(step2.getPlanId())) {
            throw new ArgumentsException("Two steps do not belong to a plan");
        }
        planService.checkCurrentUserPermission(step1.getPlanId());

        if (step1.getNextStepId().equals(step2.getId()) || step1.getPreviousStepId().equals(step2.getId())) {
            // adjacent node case: P <-> A(1) <-> B(2) <-> N
            // we want to swap A and B

            // swap the node for case P <-> B(2) <-> A(1) <-> N
            if (step1.getPreviousStepId().equals(step2.getId())) {
                StepIdOnly tmp = step1;
                step1 = step2;
                step2 = tmp;
            }

            // set link between A and N
            linkNextStep(step1, step2.getNextStepId());
            // set link between B and P
            linkPreviousStep(step2, step1.getPreviousStepId());

            // swap link between A and B
            step1.setPreviousStep(step2);
            step2.setNextStep(step1);

        } else {
            // case there is node in the middle P <-> A <-> C <-> B <-> N
            // map A to C and N, vice versa
            linkStep(step1, step2.getPreviousStepId(), step2.getNextStepId());
            // map B to C and P, vice versa
            linkStep(step2, step1.getPreviousStepId(), step1.getNextStepId());
        }

        stepRepository.updateStepPosition(step1);
        stepRepository.updateStepPosition(step2);
    }

    @Override
    public StepDto addStep(CreateStepDto dto) {
        Place place = placeService.getPlace(dto.getPlaceId());

        Step stepEntity = mapper.getStepMapper().toEntity(dto);
        stepEntity.setPlace(place);
        stepEntity.setId(0L);
        stepEntity = stepRepository.save(stepEntity);
        StepIdOnly step = getStepIdOnly(stepEntity.getId());

        PlanIdOnly plan = planService.getPlanIdOnly(dto.getPlanId());
        planService.checkCurrentUserPermission(plan);
        step.setPlan(plan);

        if (dto.getPreviousStepId() == null) {
            // insert to the head of plan
            if (plan.getFirstStepId() != null) {
                step.setNextStepId(plan.getFirstStepId());
                stepRepository.updatePreviousStepById(plan.getFirstStepId(), step.getId());
            }
            plan.setFirstStep(step);
        } else {
            if (plan.getFirstStepId() == null) {
                // insert the first step in plan
                plan.setFirstStep(step);
            } else {
                // insert step in the middle of linked list
                StepIdOnly prevStep = getStepIdOnly(dto.getPreviousStepId());

                step.setPreviousStep(prevStep);
                step.setNextStepId(prevStep.getNextStepId());
                if (prevStep.getNextStepId() != null) {
                    stepRepository.updatePreviousStepById(prevStep.getNextStepId(), step.getId());
                }
                prevStep.setNextStep(step);
                stepRepository.updateStepPosition(prevStep);
            }
        }

        planRepository.updatePlan(plan);
        stepRepository.updateStepPosition(step);
        StepDto resultDto = mapper.getStepMapper().toDto(stepEntity);
        resultDto.setNextStepId(step.getNextStepId());
        resultDto.setPreviousStepId(step.getPreviousStepId());
        return resultDto;
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
        StepIdOnly step = getStepIdOnly(id);
        PlanIdOnly plan = planService.getPlanIdOnly(step.getPlanId());
        planService.checkCurrentUserPermission(plan);
        if (step.getPreviousStepId() == null) {
            // Update the head of linked list
            if (step.getNextStepId() != null) {
                stepRepository.updatePreviousStepById(step.getNextStepId(), null);
                plan.setFirstStepId(step.getNextStepId());
            } else {
                plan.setFirstStep(null);
            }
            planRepository.updatePlan(plan);
        } else {
            // Update the next/prev link
            Long prevStepId = step.getPreviousStepId();
            Long nextStepId = step.getNextStepId();

            stepRepository.updateNextStepById(prevStepId, nextStepId);
            if (nextStepId != null) {
                stepRepository.updatePreviousStepById(nextStepId, prevStepId);
            }
        }
        stepRepository.deleteById(id);
    }

    private Step getStep(long id) {
        return stepRepository.findById(id).orElseThrow(() -> new NotFoundException(Step.class, id));
    }

    private StepIdOnly getStepIdOnly(long id) {
        return stepRepository.findIdOnlyById(id).orElseThrow(() -> new NotFoundException(Step.class, id));
    }
}