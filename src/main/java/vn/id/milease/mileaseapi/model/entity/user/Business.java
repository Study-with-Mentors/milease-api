package vn.id.milease.mileaseapi.model.entity.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Business {
    @Id
    @Column(name = "account_id")
    private Long id;

    private String name;
    private String businessType;
    private String businessCategory;

    @OneToOne
    @JoinColumn(name = "account_id")
    @MapsId
    private User user;
}
