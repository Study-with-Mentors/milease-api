package vn.id.milease.mileaseapi.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import vn.id.milease.mileaseapi.model.entity.place.Place;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction extends BaseEntity {

    private LocalDateTime createdAt;
    private double amount;
    @OneToOne
    private Place place;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "old_transaction_id")
    private Transaction oldTransaction;
}
