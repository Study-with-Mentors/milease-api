package vn.id.milease.mileaseapi.util.mapper;

import com.querydsl.core.util.StringUtils;
import org.springframework.stereotype.Component;
import vn.id.milease.mileaseapi.model.dto.PlaceDto;
import vn.id.milease.mileaseapi.model.dto.create.CreatePlaceDto;
import vn.id.milease.mileaseapi.model.dto.update.UpdatePlaceDto;
import vn.id.milease.mileaseapi.model.entity.place.Place;

import java.util.Objects;

@Component
public class PlaceMapper implements Mapper<Place, PlaceDto, CreatePlaceDto, UpdatePlaceDto> {

    //TODO [Dat, P3]: Mapping address, business.
    @Override
    public PlaceDto toDto(Place entity) {
        var result = PlaceDto.builder()
                .name(entity.getName())
                .description(entity.getDescription())
                .averageDuration(entity.getAverageDuration())
                .type(entity.getType())
                .close(entity.getClose())
                .open(entity.getOpen())
                .longitude(entity.getLongitude())
                .latitude(entity.getLatitude())
                .priceLower(entity.getPriceLower())
                .priceUpper(entity.getPriceUpper())
                .status(entity.getStatus())
                .imageUrl(entity.getImageUrl())
                .build();
        result.setId(entity.getId());
        return result;
    }

    @Override
    public Place toEntity(CreatePlaceDto dto) {
        return Place.builder()
                .longitude(dto.getLongitude())
                .latitude(dto.getLatitude())
                .averageDuration(dto.getAverageDuration())
                .close(dto.getClose())
                .open(dto.getClose())
                .description(dto.getDescription())
                .type(dto.getType())
                .status(dto.getStatus())
                .name(dto.getName())
                .imageUrl(dto.getImageUrl())
                .build();
    }

    @Override
    public void toEntity(UpdatePlaceDto dto, Place existed) {
        if (!StringUtils.isNullOrEmpty(dto.getDescription()))
            existed.setDescription(dto.getDescription());
        if (!StringUtils.isNullOrEmpty(dto.getName()))
            existed.setName(dto.getName());
        if (!Objects.isNull(dto.getStatus()))
            existed.setStatus(dto.getStatus());
        if (!Objects.isNull(dto.getType()))
            existed.setType(dto.getType());
        if (!Objects.isNull(dto.getOpen())) {
            existed.setOpen(dto.getOpen());
            if (!Objects.isNull(dto.getClose()) && !dto.getClose().isBefore(dto.getOpen()))
                existed.setClose(dto.getClose());
        }
        if (dto.getPriceLower() > 0 && dto.getPriceUpper() > dto.getPriceLower()) {
            existed.setPriceLower(dto.getPriceLower());
            existed.setPriceUpper(dto.getPriceUpper());
        }
        if (!Objects.isNull(dto.getLongitude()))
            existed.setLongitude(dto.getLatitude());
        if (!Objects.isNull(dto.getLatitude()))
            existed.setLatitude(dto.getLatitude());
        if (!Objects.isNull(dto.getImageUrl()))
            existed.setImageUrl(dto.getImageUrl());
    }
}
