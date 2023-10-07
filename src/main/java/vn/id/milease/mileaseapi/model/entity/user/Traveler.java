package vn.id.milease.mileaseapi.model.entity.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Traveler {
    @Id
    @Column(name = "account_id")
    private Long id;

    private String firstName;
    private String lastName;
    private Date birthDay;
    private LocalDateTime premiumExpiredDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    @MapsId
    private User user;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "latest_transaction_id")
    private TravelerTransaction latestTransaction;

    @Enumerated(EnumType.STRING)
    private TravelerStatus status;
}
