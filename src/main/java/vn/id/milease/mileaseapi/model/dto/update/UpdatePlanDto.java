package vn.id.milease.mileaseapi.model.dto.update;

import vn.id.milease.mileaseapi.model.dto.BaseDto;
import vn.id.milease.mileaseapi.model.entity.plan.PlanStatus;

import java.time.LocalDateTime;

public class UpdatePlanDto extends BaseDto {
    private LocalDateTime start;
    private LocalDateTime end;
    private String name;
    private PlanStatus status;
}
