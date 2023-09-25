package vn.id.milease.mileaseapi.service;

import vn.id.milease.mileaseapi.model.dto.PageResult;
import vn.id.milease.mileaseapi.model.dto.PlaceDto;
import vn.id.milease.mileaseapi.model.dto.create.CreatePlaceDto;
import vn.id.milease.mileaseapi.model.dto.search.PlaceSearchDto;
import vn.id.milease.mileaseapi.model.dto.update.UpdatePlaceDto;
import vn.id.milease.mileaseapi.model.entity.place.Place;

import java.util.concurrent.CompletableFuture;

public interface PlaceService {
    CompletableFuture<PageResult<PlaceDto>> getPlacesAsync(PlaceSearchDto searchDto) throws InterruptedException;
    PageResult<PlaceDto> getPlaces(PlaceSearchDto searchDto);

    PlaceDto addPlace(CreatePlaceDto dto);

    PlaceDto updatePlace(UpdatePlaceDto dto);

    void deletePlace(long id);

    PlaceDto getPlacesById(long id);

    Place getPlace(long id);
}
