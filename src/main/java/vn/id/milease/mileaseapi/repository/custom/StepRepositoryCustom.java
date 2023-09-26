package vn.id.milease.mileaseapi.repository.custom;

import vn.id.milease.mileaseapi.model.entity.step.StepIdOnly;

public interface StepRepositoryCustom {
    void updateStepPosition(StepIdOnly step);

    void updateNextStepById(Long id, Long nextStepId);

    void updatePreviousStepById(Long id, Long previousStepId);
}
