package vn.id.milease.mileaseapi.model.dto;

import vn.id.milease.mileaseapi.model.entity.plan.PlanStatus;

import java.time.LocalDateTime;

public class PlanDto extends BaseDto {
    private LocalDateTime start;
    private LocalDateTime end;
    private String name;
    private PlanStatus status;
}
