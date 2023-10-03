package vn.id.milease.mileaseapi.model.dto;

import lombok.Getter;
import lombok.Setter;
import vn.id.milease.mileaseapi.model.entity.step.StepType;

@Getter
@Setter
public class StepDto extends BaseDto {
    private StepType type;
    private Float duration;
    private Float distance;
    private Long nextStepId;
    private Long previousStepId;
    private PlaceDto place;
    private Float longitude;
    private Float latitude;
    private String placeName;
    private String addressString;
}
