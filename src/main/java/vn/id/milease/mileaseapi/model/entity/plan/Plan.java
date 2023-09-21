package vn.id.milease.mileaseapi.model.entity.plan;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import vn.id.milease.mileaseapi.model.entity.BaseEntity;
import vn.id.milease.mileaseapi.model.entity.step.Step;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Plan extends BaseEntity {
    private LocalDateTime start;
    private LocalDateTime end;
    private String name;
    @Enumerated(EnumType.STRING)
    private PlanStatus status;
    @OneToMany(mappedBy = "plan")
    private List<Step> steps;
    // TODO [Duy, P2] Missing province
    @OneToOne
    private Step firstStep;
    // TODO [Duy, P1] Missing user
}
