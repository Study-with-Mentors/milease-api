package vn.id.milease.mileaseapi.service;

import vn.id.milease.mileaseapi.model.dto.PageResult;
import vn.id.milease.mileaseapi.model.dto.PlaceDto;
import vn.id.milease.mileaseapi.model.dto.create.CreatePlaceDto;
import vn.id.milease.mileaseapi.model.dto.search.PlaceSearchDto;
import vn.id.milease.mileaseapi.model.dto.update.UpdatePlaceDto;

import java.util.List;

public interface PlaceService {
    PageResult<PlaceDto> getPlaces(PlaceSearchDto searchDto);

    PlaceDto addPlace(CreatePlaceDto dto);

    PlaceDto updatePlace(UpdatePlaceDto dto);

    void deletePlace(long id);

    List<PlaceDto> getPlacesByIds(List<Long> ids);
}
