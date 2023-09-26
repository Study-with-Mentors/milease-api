package vn.id.milease.mileaseapi.model.entity.plan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import vn.id.milease.mileaseapi.model.entity.step.StepIdOnly;

@Getter
@Setter
@AllArgsConstructor
@Data
public class PlanIdOnly {
    Long id;
    Long userId;
    Long firstStepId;

    public void setFirstStep(StepIdOnly step) {
        if (step != null) {
            firstStepId = step.getId();
        } else {
            firstStepId = null;
        }
    }
}
