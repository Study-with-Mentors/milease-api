package vn.id.milease.mileaseapi.model.dto.search;

import de.mobiuscode.nameof.Name;
import lombok.*;
import vn.id.milease.mileaseapi.model.entity.place.Place;
import vn.id.milease.mileaseapi.model.entity.place.PlaceStatus;
import vn.id.milease.mileaseapi.model.entity.place.PlaceType;

import java.util.List;

@Getter
@Setter
public class PlaceSearchDto extends BaseSearchDto {
    private long id;
    private String name = "";
    private List<PlaceType> types;
    private PlaceStatus status = PlaceStatus.AVAILABLE;
    private OrderBy orderBy;
    private float durationFrom;
    private float durationTo;

    //Use library 'mobiuscode' to have a syntax nameof like C# in order to avoid magic string.
    //Example: Instead of writing raw string "displayIndex", Name.of(Place.class, Place::getDisplayIndex) will return "displayIndex"
    //To take value of Enum call 'getNameOfProperty()' function. Example:
    //
    // PlaceSearchDto search = new ....
    // string expectPropertyName = search.getOrderBy().getNameOfProperty();
    //
    public enum OrderBy {
        DISPLAY_INDEX {
            @Override
            public String getNameOfProperty() {
                return Name.of(Place.class, Place::getDisplayIndex);
            }
        },
        ID {
            @Override
            public String getNameOfProperty() {
                return Name.of(Place.class, Place::getId);
            }
        },
        NAME {
            @Override
            public String getNameOfProperty() {
                return Name.of(Place.class, Place::getName);
            }
        };
        public abstract String getNameOfProperty();
    }
}
