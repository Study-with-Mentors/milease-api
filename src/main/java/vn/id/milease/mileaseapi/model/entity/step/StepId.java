package vn.id.milease.mileaseapi.model.entity.step;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class StepId implements Serializable {
    private long planId;
    private long placeId;
}
