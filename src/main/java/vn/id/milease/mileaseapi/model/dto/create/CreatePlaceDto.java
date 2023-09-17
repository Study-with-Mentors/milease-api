package vn.id.milease.mileaseapi.model.dto.create;

import lombok.Getter;
import lombok.Setter;
import vn.id.milease.mileaseapi.model.entity.place.PlaceStatus;
import vn.id.milease.mileaseapi.model.entity.place.PlaceType;

import java.time.LocalDateTime;
@Getter
@Setter
public class CreatePlaceDto {
    private String name;
    private float priceLower = 0;
    private float priceUpper = 0;
    private LocalDateTime open;
    private LocalDateTime close;
    private String description;
    private float averageDuration;
    private PlaceType type = PlaceType.ENTERTAINMENT;
    private PlaceStatus status = PlaceStatus.AVAILABLE;
    private long addressId;
    private long businessId;
}
