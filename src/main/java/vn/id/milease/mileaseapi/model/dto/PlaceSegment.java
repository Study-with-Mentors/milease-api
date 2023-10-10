package vn.id.milease.mileaseapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PlaceSegment {
    private long id;
    private Float longitude;
    private Float latitude;
}
