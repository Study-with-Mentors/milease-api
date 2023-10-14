package vn.id.milease.mileaseapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class SuggestDto {
    @NotNull
    private PlaceSegment place1;
    @NotNull
    private PlaceSegment place2;
    @Min(1)
    private int placesSize;
    @Nullable
    private List<Long> selectedPlaces;
}
