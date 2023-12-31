package vn.id.milease.mileaseapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.id.milease.mileaseapi.model.entity.administrativeunit.Ward;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Address extends BaseEntity {
    @ManyToOne
    @JoinColumn
    private Ward ward;

    private String addressLine;
}
