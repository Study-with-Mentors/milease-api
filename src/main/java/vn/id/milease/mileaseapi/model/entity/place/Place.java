package vn.id.milease.mileaseapi.model.entity.place;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import vn.id.milease.mileaseapi.model.entity.Address;
import vn.id.milease.mileaseapi.model.entity.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private float priceLower;
    private float priceUpper;
    private LocalDateTime open;
    private LocalDateTime close;
    private String description;
    @JsonIgnore
    private int displayIndex;
    private float averageDuration;
    @Enumerated(EnumType.STRING)
    private PlaceType type;
    @Enumerated(EnumType.STRING)
    private PlaceStatus status;
    @OneToOne
    @JoinColumn
    private Address address;
}
