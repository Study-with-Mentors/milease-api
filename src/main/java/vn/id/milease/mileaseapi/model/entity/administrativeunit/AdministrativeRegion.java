package vn.id.milease.mileaseapi.model.entity.administrativeunit;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "administrative_regions")
@Getter
public class AdministrativeRegion {
    @Id
    private Integer id;
    private String name;
    private String nameEn;
    private String codeName;
    private String codeNameEn;

    @OneToMany(mappedBy = "administrativeRegion")
    private List<Province> provinces;
}
