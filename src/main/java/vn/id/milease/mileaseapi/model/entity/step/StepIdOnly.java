package vn.id.milease.mileaseapi.model.entity.step;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import vn.id.milease.mileaseapi.model.entity.plan.PlanIdOnly;

@Getter
@Setter
@AllArgsConstructor
@Data
public class StepIdOnly {
    private Long id;
    private Long previousStepId;
    private Long nextStepId;
    private Long planId;

    public void setNextStep(StepIdOnly stepId) {
        if (stepId == null) {
            nextStepId = null;
        } else {
            nextStepId = stepId.getId();
        }
    }

    public void setPreviousStep(StepIdOnly stepId) {
        if (stepId == null) {
            previousStepId = null;
        } else {
            previousStepId = stepId.getId();
        }
    }

    public void setPlan(PlanIdOnly plan) {
        if (plan != null) {
            planId = plan.getId();
        }
    }
}
