package vn.id.milease.mileaseapi.model.entity.step;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import vn.id.milease.mileaseapi.model.entity.BaseEntity;
import vn.id.milease.mileaseapi.model.entity.place.Place;
import vn.id.milease.mileaseapi.model.entity.plan.Plan;

import javax.persistence.*;

@Entity
@Setter
@Getter
@ToString
public class Step extends BaseEntity {
    private StepType type;
    private Float duration;
    // TODO [Duy, P3] distance should be refactor (maybe client will call map API to figure out)
    private Float distance;
    private Float longitude;
    private Float latitude;
    @ManyToOne
    @JoinColumn
    private Plan plan;
    @ManyToOne
    @JoinColumn
    private Place place;

    // step will be implemented as linked list
    @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Step nextStep;
    @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Step previousStep;
}
