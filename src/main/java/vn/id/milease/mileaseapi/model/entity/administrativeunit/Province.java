package vn.id.milease.mileaseapi.model.entity.administrativeunit;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "provinces")
@Getter
public class Province {
    @Id
    private String code;
    private String name;
    private String nameEn;
    private String fullName;
    private String fullNameEn;
    private String codeName;

    @ManyToOne
    @JoinColumn
    private AdministrativeUnit administrativeUnit;

    @ManyToOne
    @JoinColumn
    private AdministrativeRegion administrativeRegion;

    @OneToMany(mappedBy = "province")
    private List<District> districts;
}
