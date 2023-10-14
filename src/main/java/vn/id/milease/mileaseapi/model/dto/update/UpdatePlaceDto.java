package vn.id.milease.mileaseapi.model.dto.update;

import lombok.Getter;
import lombok.Setter;
import vn.id.milease.mileaseapi.model.dto.BaseDto;
import vn.id.milease.mileaseapi.model.entity.place.PlaceStatus;
import vn.id.milease.mileaseapi.model.entity.place.PlaceType;

import javax.annotation.Nullable;
import java.time.LocalTime;

@Getter
@Setter
public class UpdatePlaceDto extends BaseDto {
    @Nullable
    private String name;
    private float priceLower;
    private float priceUpper;
    @Nullable
    private LocalTime open;
    @Nullable
    private LocalTime close;
    @Nullable
    private String description;
    private float averageDuration;
    private Float longitude;
    private Float latitude;
    @Nullable
    private PlaceType type;
    @Nullable
    private PlaceStatus status;
    @Nullable
    private String imageUrl;
    private long addressId;
    private long businessId;
}
