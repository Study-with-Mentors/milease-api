package vn.id.milease.mileaseapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PremiumPlan extends BaseEntity {
    private String name;

    private Double price;
    private Integer duration;

    @Enumerated(EnumType.STRING)
    private TimeUnit timeUnit;

    @Enumerated(EnumType.STRING)
    private PremiumPlanStatus status;
}
