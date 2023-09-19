package vn.id.milease.mileaseapi.model.entity.step;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import vn.id.milease.mileaseapi.model.entity.BaseEntity;
import vn.id.milease.mileaseapi.model.entity.place.Place;
import vn.id.milease.mileaseapi.model.entity.plan.Plan;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@ToString
public class Step extends BaseEntity{
    // TODO [Duy, P1] : change this to linked list
    private LocalDateTime start;
    private LocalDateTime end;
    private StepType type;
    private Integer index;
    private Float duration;
    private Float distance;
    @ManyToOne
    @JoinColumn
    private Plan plan;
    @ManyToOne
    @JoinColumn
    private Place place;
}
