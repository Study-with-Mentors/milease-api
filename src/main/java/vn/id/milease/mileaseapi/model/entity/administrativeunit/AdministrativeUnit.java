package vn.id.milease.mileaseapi.model.entity.administrativeunit;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "administrative_units")
@Getter
public class AdministrativeUnit {
    @Id
    private Integer id;
    private String fullName;
    private String fullNameEn;
    private String shortName;
    private String shortNameEn;
    private String codeName;
    private String codeNameEn;
}
