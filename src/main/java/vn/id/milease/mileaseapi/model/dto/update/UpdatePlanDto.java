package vn.id.milease.mileaseapi.model.dto.update;

import lombok.Getter;
import lombok.Setter;
import vn.id.milease.mileaseapi.model.dto.BaseDto;
import vn.id.milease.mileaseapi.model.entity.plan.PlanStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class UpdatePlanDto extends BaseDto {
    private LocalDateTime start;
    private LocalDateTime end;
    private String name;
    private PlanStatus status;
}
