package vn.id.milease.mileaseapi.service;

import vn.id.milease.mileaseapi.model.dto.StepDto;
import vn.id.milease.mileaseapi.model.dto.create.CreateStepDto;
import vn.id.milease.mileaseapi.model.dto.create.CreateTailStepDto;
import vn.id.milease.mileaseapi.model.dto.update.UpdateStepDto;
import vn.id.milease.mileaseapi.model.entity.step.StepIdOnly;

import java.util.List;

public interface StepService {
    StepDto getStepById(long id);

    // TODO [Duy, P3] Maybe with some filter
    List<StepDto> getStepByPlanId(long planId);

    void swapStep(long step1Id, long step2Id);

    void moveStep(long stepId, long toStepId);

    StepDto addStep(CreateStepDto dto);

    StepDto addTailStep(CreateTailStepDto dto);

    StepDto updateStep(UpdateStepDto dto);

    void deleteStep(long id);

    StepIdOnly getLastStepOfPlan(long planId);
}
