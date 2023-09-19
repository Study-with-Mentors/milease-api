package vn.id.milease.mileaseapi.service;

import vn.id.milease.mileaseapi.model.dto.StepDto;
import vn.id.milease.mileaseapi.model.dto.create.CreateStepDto;
import vn.id.milease.mileaseapi.model.dto.update.UpdateStepDto;

import java.util.List;

public interface StepService {
    StepDto getStepById(long id);
    // TODO [Duy, P3] Maybe with some filter
    List<StepDto> getStepByPlanId(long planId);

    StepDto addStep(CreateStepDto dto);
    StepDto updateStep(UpdateStepDto dto);
    void deleteStep(long id);
}