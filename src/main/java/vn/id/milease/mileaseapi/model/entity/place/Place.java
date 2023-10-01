package vn.id.milease.mileaseapi.model.entity.place;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import vn.id.milease.mileaseapi.model.entity.Address;
import vn.id.milease.mileaseapi.model.entity.BaseEntity;
import vn.id.milease.mileaseapi.model.entity.Transaction;
import vn.id.milease.mileaseapi.model.entity.step.Step;
import vn.id.milease.mileaseapi.model.entity.user.Business;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Place extends BaseEntity {
    @Column(nullable = false)
    private String name;
    private Float priceLower;
    private Float priceUpper;
    private LocalTime open;
    private LocalTime close;
    private String description;
    private String image;
    @JsonIgnore
    private Long displayIndex;
    private LocalDateTime createdAt;
    private Float averageDuration;
    @Enumerated(EnumType.STRING)
    private PlaceType type;
    @Enumerated(EnumType.STRING)
    private PlaceStatus status;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Address address;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id")
    private Business business;
    @OneToMany(mappedBy = "place")
    private List<Step> steps;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lastest_transaction_id")
    private Transaction lastestTransaction;
}
