package vn.id.milease.mileaseapi.model.dto.create;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import vn.id.milease.mileaseapi.model.entity.step.StepType;

@Getter
@Setter
public class CreateStepDto {
    // TODO [Duy, P2] validation
    @JsonIgnore
    private long planId;
    private Long prevStepId;
    private long placeId;
    // TODO [Duy, P3] further specify if we can create/update step type
    private StepType type;
    private Float duration;
    private Float distance;
}
