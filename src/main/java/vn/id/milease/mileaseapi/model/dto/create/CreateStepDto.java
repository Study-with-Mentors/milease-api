package vn.id.milease.mileaseapi.model.dto.create;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import vn.id.milease.mileaseapi.model.entity.step.StepType;

@Getter
@Setter
public class CreateStepDto {
    @JsonIgnore // plan id is get from the endpoint
    private long planId;
    /**
     * The <code>previousStepId</code> indicates that this newly created step
     * is the next step of <code>previousStepId.</code><br>
     * <code>null</code> <code>previousStepId</code> indicates that this newly created step is the head of the plan.
     */
    private Long previousStepId;
    // TODO [Duy, P3] placeId can be null if step type is Transport
    private long placeId;
    // TODO [Duy, P3] further specify if we can create/update type of step
    private StepType type;
    /**
     * <code>null</code> duration means the system will use the average duration of place
     */
    private Float duration;
    private Float stopoverDuration;
    private Float distance;
    private Float longitude;
    private Float latitude;
    private String placeName;
    private String addressString;
}
