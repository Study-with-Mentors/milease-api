package vn.id.milease.mileaseapi.model.dto.search;

import de.mobiuscode.nameof.Name;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import vn.id.milease.mileaseapi.model.entity.place.Place;
import vn.id.milease.mileaseapi.model.entity.place.PlaceStatus;
import vn.id.milease.mileaseapi.model.entity.place.PlaceType;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlaceSearchDto extends BaseSearchDto {
    private List<Long> ids;
    private String name;
    private List<PlaceType> types;
    private List<PlaceStatus> statuses;
    @Nullable
    private PlaceProperty orderBy;
    private float durationFrom;
    private float durationTo;

    @Override
    public String getOrderBy() {
        return orderBy != null ? orderBy.getNameOfProperty() : PlaceProperty.DISPLAY_INDEX.getNameOfProperty();
    }

    //Use library 'mobiuscode' to have a syntax nameof like C# in order to avoid magic string.
    //Example: Instead of writing raw string "displayIndex", Name.of(Place.class, Place::getDisplayIndex) will return "displayIndex"
    //To take value of Enum call 'getNameOfProperty()' function. Example:
    //
    // PlaceSearchDto search = new ....
    // string expectPropertyName = search.getOrderBy().getNameOfProperty();
    //
    public enum PlaceProperty implements EntityProperty {
        DISPLAY_INDEX(Place::getDisplayIndex),
        ID(Place::getId),
        NAME(Place::getName);

        private final Function<Place, ?> property;

        PlaceProperty(Function<Place, ?> property) {
            this.property = property;
        }

        @Override
        public String getNameOfProperty() {
            return Name.of(Place.class, property);
        }
    }
}
