package vn.id.milease.mileaseapi.model.dto;

import lombok.*;
import vn.id.milease.mileaseapi.model.entity.place.PlaceStatus;
import vn.id.milease.mileaseapi.model.entity.place.PlaceType;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlaceDto extends BaseDto{
    private String name;
    private float priceLower;
    private float priceUpper;
    private LocalDateTime open;
    private LocalDateTime close;
    private String description;
    private float averageDuration;
    private PlaceType type;
    private PlaceStatus status;
}
