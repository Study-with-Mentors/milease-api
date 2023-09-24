package vn.id.milease.mileaseapi.model.entity.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.id.milease.mileaseapi.model.entity.Address;
import vn.id.milease.mileaseapi.model.entity.BaseEntity;
import vn.id.milease.mileaseapi.model.entity.Phone;
import vn.id.milease.mileaseapi.model.entity.Subscription;
import vn.id.milease.mileaseapi.model.entity.plan.Plan;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "account")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class User extends BaseEntity {
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private Traveler traveler;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private Business business;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private Subscription subscription;

    @OneToMany(mappedBy = "user")
    private List<Address> addresses;

    @OneToMany(mappedBy = "user")
    private List<Phone> phones;

    @OneToMany(mappedBy = "user")
    private List<Plan> plans;
}
