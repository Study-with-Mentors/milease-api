package vn.id.milease.mileaseapi.model.dto.update;

import lombok.Getter;
import lombok.Setter;
import vn.id.milease.mileaseapi.model.dto.BaseDto;
import vn.id.milease.mileaseapi.model.entity.place.PlaceStatus;
import vn.id.milease.mileaseapi.model.entity.place.PlaceType;

import javax.annotation.Nullable;
import java.time.LocalDateTime;

@Getter
@Setter
public class UpdatePlaceDto extends BaseDto {
    @Nullable
    private String name;
    private float priceLower;
    private float priceUpper;
    @Nullable
    private LocalDateTime open;
    @Nullable
    private LocalDateTime close;
    @Nullable
    private String description;
    private float averageDuration;
    @Nullable
    private PlaceType type;
    @Nullable
    private PlaceStatus status;
    private long addressId;
    private long businessId;
}
