package vn.id.milease.mileaseapi.model.dto.create;

import lombok.Getter;
import lombok.Setter;
import vn.id.milease.mileaseapi.model.entity.place.PlaceStatus;
import vn.id.milease.mileaseapi.model.entity.place.PlaceType;

import java.time.LocalTime;

@Getter
@Setter
public class CreatePlaceDto {
    private String name;
    private float priceLower;
    private float priceUpper;
    private LocalTime open;
    private LocalTime close;
    private String description;
    private float averageDuration;
    private PlaceType type = PlaceType.ENTERTAINMENT;
    private PlaceStatus status;
    private String imageUrl;
    private Float longitude;
    private Float latitude;
    private long addressId;
    private long businessId;
}
