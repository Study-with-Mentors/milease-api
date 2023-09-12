package vn.id.milease.mileaseapi.model.entity.user;

import vn.id.milease.mileaseapi.model.entity.BaseEntity;

import javax.persistence.Entity;

@Entity
public class User extends BaseEntity {
    private String email;
    private String password;
    private String name;
}
