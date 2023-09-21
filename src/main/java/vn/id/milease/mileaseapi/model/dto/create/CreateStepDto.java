package vn.id.milease.mileaseapi.model.dto.create;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import vn.id.milease.mileaseapi.model.entity.step.StepType;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreateStepDto {
    @JsonIgnore // plan id is get from the endpoint
    private long planId;
    /**
     * the <code>prevStepId</code> indicate the next step of <code>prevStepId</code> is the newly created step
     * <code>null</code> <code>prevStepId</code> indicate the newly created step will be the head of plan
     */
    private Long prevStepId;
    @NotNull
    @Min(1)
    private long placeId;
    // TODO [Duy, P3] further specify if we can create/update step type
    private StepType type;
    /**
     * <code>null</code> duration means the system will use the average duration of place
     */
    private Float duration;
    private Float distance;
}
