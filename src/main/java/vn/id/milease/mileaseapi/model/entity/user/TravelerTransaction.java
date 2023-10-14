package vn.id.milease.mileaseapi.model.entity.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import vn.id.milease.mileaseapi.model.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TravelerTransaction extends BaseEntity {
    private float amount;
    @CreatedDate
    private LocalDateTime createdAt;
    @OneToOne
    private Traveler traveler;
    @OneToOne
    @JoinColumn(name = "old_transaction_id")
    private TravelerTransaction oldTransaction;
}
