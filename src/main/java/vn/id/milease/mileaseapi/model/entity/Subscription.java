package vn.id.milease.mileaseapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.id.milease.mileaseapi.model.entity.user.User;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Subscription extends BaseEntity {
    private Date startDate;
    private Date endDate;

    @OneToOne
    @JoinColumn(name = "account_id")
    private User user;

    @OneToMany(mappedBy = "subscription")
    private List<Invoice> invoices;
}
