package vn.id.milease.mileaseapi.model.entity.place;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import vn.id.milease.mileaseapi.model.entity.Address;
import vn.id.milease.mileaseapi.model.entity.BaseEntity;
import vn.id.milease.mileaseapi.model.entity.step.Step;
import vn.id.milease.mileaseapi.model.entity.user.Business;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    private LocalDateTime open;
    private LocalDateTime close;
    private String description;
    @JsonIgnore
    private int displayIndex;
    private Float averageDuration;
    @Enumerated(EnumType.STRING)
    private PlaceType type;
    @Enumerated(EnumType.STRING)
    private PlaceStatus status;
    @OneToOne
    @JoinColumn
    private Address address;
    @ManyToOne
    @JoinColumn(name = "business_id")
    private Business business;
    @OneToMany(mappedBy = "place")
    private List<Step> steps;
}
