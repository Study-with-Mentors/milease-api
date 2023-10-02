package vn.id.milease.mileaseapi.model.dto;

import lombok.*;
import vn.id.milease.mileaseapi.model.entity.place.PlaceStatus;
import vn.id.milease.mileaseapi.model.entity.place.PlaceType;

import java.time.LocalTime;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlaceDto extends BaseDto {
    private String name;
    private Float priceLower;
    private Float priceUpper;
    private LocalTime open;
    private LocalTime close;
    private String description;
    private Float averageDuration;
    private PlaceType type;
    private PlaceStatus status;
    private String imageUrl;
}
