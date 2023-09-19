package vn.id.milease.mileaseapi.util.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import vn.id.milease.mileaseapi.model.dto.StepDto;
import vn.id.milease.mileaseapi.model.dto.create.CreateStepDto;
import vn.id.milease.mileaseapi.model.dto.update.UpdateStepDto;
import vn.id.milease.mileaseapi.model.entity.step.Step;

@RequiredArgsConstructor
@Component
public class StepMapper implements Mapper<Step, StepDto, CreateStepDto, UpdateStepDto> {
    private final ModelMapper mapper;
    @Override
    public Step toEntity(CreateStepDto createStepDto) {
        return mapper.map(createStepDto, Step.class);
    }

    @Override
    public void toEntity(UpdateStepDto updateStepDto, Step step) {
        mapper.map(updateStepDto, step);
    }

    @Override
    public StepDto toDto(Step step) {
        return mapper.map(step, StepDto.class);
    }
}
