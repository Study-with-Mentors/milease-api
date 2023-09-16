package vn.id.milease.mileaseapi.model.entity.plan;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import vn.id.milease.mileaseapi.model.entity.BaseEntity;
import vn.id.milease.mileaseapi.model.entity.step.Step;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
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
}
