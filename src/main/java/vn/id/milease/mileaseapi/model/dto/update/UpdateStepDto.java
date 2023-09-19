package vn.id.milease.mileaseapi.model.dto.update;

import lombok.Getter;
import lombok.Setter;
import vn.id.milease.mileaseapi.model.dto.BaseDto;
import vn.id.milease.mileaseapi.model.entity.step.StepType;

import java.time.LocalDateTime;

@Getter
@Setter
public class UpdateStepDto extends BaseDto {
    private LocalDateTime start;
    private LocalDateTime end;
    private StepType type;
    private Integer index;
    private Float duration;
    private Float distance;
}
