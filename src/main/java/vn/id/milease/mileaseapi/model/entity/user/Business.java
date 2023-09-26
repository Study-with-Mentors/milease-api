package vn.id.milease.mileaseapi.model.entity.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.id.milease.mileaseapi.model.entity.place.Place;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    @MapsId
    private User user;

    @OneToMany(mappedBy = "business")
    private List<Place> places;
}
