package vn.id.milease.mileaseapi.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class TravelerDto {
    private Long id;
    private String fullName;
    private String imageUrl;
    private LocalDateTime createdTime;
    private boolean isPremium;
}
