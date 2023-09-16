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
public class Step {
    @EmbeddedId
    private StepId stepId;
    private LocalDateTime start;
    private LocalDateTime end;
    private StepType type;
    private int index;
    private float duration;
    private float distance;
    @ManyToOne
    @MapsId("planId")
    private Plan plan;
    @ManyToOne
    @MapsId("placeId")
    private Place place;
    @Version
    private long version;
}
