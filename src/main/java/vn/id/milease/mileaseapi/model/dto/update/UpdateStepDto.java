package vn.id.milease.mileaseapi.model.dto.update;

import lombok.Getter;
import lombok.Setter;
import vn.id.milease.mileaseapi.model.dto.BaseDto;

@Getter
@Setter
public class UpdateStepDto extends BaseDto {
    private Float duration;
    private Float stopoverDuration;
    private Float distance;
}
