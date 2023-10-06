package vn.id.milease.mileaseapi.model.entity.step;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import vn.id.milease.mileaseapi.model.entity.BaseEntity;
import vn.id.milease.mileaseapi.model.entity.place.Place;
import vn.id.milease.mileaseapi.model.entity.plan.Plan;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
@Setter
@Getter
@ToString
public class Step extends BaseEntity {
    private StepType type;
    /**
     * Travel duration to this step in second.
     * 0 or null if this is the first step.
     */
    private Float duration;
    /**
     * Stopover duration in second.
     * 0 or null if this is the first or last step.
     */
    private Float stopoverDuration;
    // TODO [Duy, P3] distance should be refactor (maybe client will call map API to figure out)
    /**
     * Travel distance to this step in meter.
     */
    private Float distance;
    private Float longitude;
    private Float latitude;
    private String placeName;
    private String addressString;
    @ManyToOne
    @JoinColumn
    private Plan plan;
    @ManyToOne
    @JoinColumn
    private Place place;

    // step will be implemented as linked list
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Step nextStep;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Step previousStep;
}
