package vn.id.milease.mileaseapi.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {
    @Id
    @GeneratedValue
    protected Long id;
    @Version
    protected Long version;
}
