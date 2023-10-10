package vn.id.milease.mileaseapi.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import vn.id.milease.mileaseapi.model.dto.PlaceSegment;
import vn.id.milease.mileaseapi.repository.PlaceRepository;

import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ServiceConfiguration {
    private final PlaceRepository placeRepository;

    @Bean
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public Map<Long, PlaceSegment> placeSegmentList() {
        return placeRepository.findAll()
                .stream()
                .map(v ->
                        PlaceSegment.builder()
                                .latitude(v.getLatitude())
                                .longitude(v.getLongitude())
                                .id(v.getId())
                                .build()
                )
                .collect(Collectors.toMap(PlaceSegment::getId, v -> v));
    }
}
