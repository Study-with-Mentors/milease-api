package vn.id.milease.mileaseapi.util;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import vn.id.milease.mileaseapi.model.dto.PlaceSegment;
import vn.id.milease.mileaseapi.repository.PlaceRepository;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ServicePreloadData {
    public static final Map<Long, PlaceSegment> placeSegmentMap = new HashMap<>();
    private final PlaceRepository placeRepository;

    @Bean
    @Scope(scopeName = org.springframework.web.context.WebApplicationContext.SCOPE_REQUEST)
    public Map<Long, PlaceSegment> placeSegmentList() {
        if (placeSegmentMap.isEmpty())
            placeRepository
                    .findAll()
                    .forEach(v -> placeSegmentMap.put(v.getId(), PlaceSegment.builder()
                            .latitude(v.getLatitude())
                            .longitude(v.getLongitude())
                            .id(v.getId())
                            .build())
                    );
        return placeSegmentMap;
    }
}
