package vn.id.milease.mileaseapi.model.entity.administrativeunit;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "districts")
@Getter
public class District {
    @Id
    private String code;
    private String name;
    private String nameEn;
    private String fullName;
    private String fullNameEn;
    private String codeName;

    @ManyToOne
    @JoinColumn
    private Province province;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private AdministrativeUnit administrativeUnit;

    @OneToMany(mappedBy = "district")
    private List<Ward> wards;
}
