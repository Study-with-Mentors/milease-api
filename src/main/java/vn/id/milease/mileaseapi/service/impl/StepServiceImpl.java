package vn.id.milease.mileaseapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.id.milease.mileaseapi.model.dto.StepDto;
import vn.id.milease.mileaseapi.model.dto.create.CreateStepDto;
import vn.id.milease.mileaseapi.model.dto.update.UpdateStepDto;
import vn.id.milease.mileaseapi.model.entity.place.Place;
import vn.id.milease.mileaseapi.model.entity.plan.Plan;
import vn.id.milease.mileaseapi.model.entity.step.Step;
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
        // TODO [Duy, P1] check user permission
        checkPlanExist(planId);
        List<Step> steps = stepRepository.findByPlanId(planId);
        return steps.stream()
                .map(mapper.getStepMapper()::toDto)
                .toList();
    }

    @Override
    public StepDto addStep(CreateStepDto dto) {
        // TODO [Duy, P1] check user permission
        // TODO [Duy, P1] update step links after implement linked list
        Plan plan = planService.getPlan(dto.getPlanId());
        Place place = placeService.getPlace(dto.getPlaceId());

        Step step = mapper.getStepMapper().toEntity(dto);
        step.setPlan(plan);
        step.setPlace(place);
        step.setId(0L);

        step = stepRepository.save(step);
        return mapper.getStepMapper().toDto(step);
    }

    @Override
    public StepDto updateStep(UpdateStepDto dto) {
        // TODO [Duy, P1] check user permission
        // TODO [Duy, P1] update step links after implement linked list
        Step step = getStep(dto.getId());
        mapper.getStepMapper().toEntity(dto, step);

        step = stepRepository.save(step);
        return mapper.getStepMapper().toDto(step);
    }

    @Override
    public void deleteStep(long id) {
        // TODO [Duy, P1] check user permission
        // TODO [Duy, P1] update step links after implement linked list
        stepRepository.deleteById(id);
    }

    private Step getStep(long id) {
        // TODO [Duy, P1] check user permission
        return stepRepository.findById(id).orElseThrow(() -> new NotFoundException(Step.class, id));
    }

    private void checkPlanExist(long dto) {
        if (!planRepository.existsById(dto)) {
            throw new NotFoundException(Plan.class, dto);
        }
    }
}
