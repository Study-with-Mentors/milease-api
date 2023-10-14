package vn.id.milease.mileaseapi.model.dto.search;

import de.mobiuscode.nameof.Name;
import lombok.Getter;
import lombok.Setter;
import vn.id.milease.mileaseapi.model.entity.user.User;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.function.Function;


@Getter
@Setter
public class UserSearchDto extends BaseSearchDto {
    @Nullable
    private UserSearchDto.UserProperty orderBy;
    private LocalDateTime lowerDate = LocalDateTime.of(1999, 1, 1, 1, 1, 1);
    private LocalDateTime upperDate = LocalDateTime.of(3000, 1, 1, 1, 1, 1);

    @Override
    public String getOrderBy() {
        return orderBy != null ? orderBy.getNameOfProperty() : UserProperty.ID.getNameOfProperty();
    }

    public enum UserProperty implements EntityProperty {
        CREATED_TIME(User::getCreatedTime),
        ID(User::getId),
        NAME(User::getEmail);

        private final Function<User, ?> property;

        UserProperty(Function<User, ?> property) {
            this.property = property;
        }

        @Override
        public String getNameOfProperty() {
            return Name.of(User.class, property);
        }
    }
}
