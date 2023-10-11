package vn.id.milease.mileaseapi.util.mapper;

import org.springframework.stereotype.Component;
import vn.id.milease.mileaseapi.model.dto.TravelerDto;
import vn.id.milease.mileaseapi.model.entity.user.User;

import java.time.LocalDateTime;

@Component
public class UserMapper {
    public TravelerDto toDto(User user) {
        return TravelerDto.builder()
                .createdTime(user.getCreatedTime())
                .fullName(user.getTraveler().getFullName())
                .imageUrl(user.getImageUrl())
                .isPremium(user.getTraveler().getPremiumExpiredDate().isAfter(LocalDateTime.now()))
                .id(user.getId()).build();
    }
}
