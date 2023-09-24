package vn.id.milease.mileaseapi.model.entity.plan;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import vn.id.milease.mileaseapi.model.entity.BaseEntity;
import vn.id.milease.mileaseapi.model.entity.step.Step;
import vn.id.milease.mileaseapi.model.entity.user.User;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
    @OneToMany(mappedBy = "plan", cascade = CascadeType.REMOVE)
    private List<Step> steps;
    // TODO [Duy, P3] Missing province
    @OneToOne(fetch = FetchType.LAZY)
    private Step firstStep;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;
}
