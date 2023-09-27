package vn.id.milease.mileaseapi.model.dto.create;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreatePlanDto {
    private LocalDateTime start;
    private LocalDateTime end;
    private String name;
}
