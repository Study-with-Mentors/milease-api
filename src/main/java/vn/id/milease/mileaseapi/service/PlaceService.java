package vn.id.milease.mileaseapi.service;

import vn.id.milease.mileaseapi.model.dto.PageResult;
import vn.id.milease.mileaseapi.model.dto.PlaceDto;
import vn.id.milease.mileaseapi.model.dto.PlaceSegment;
import vn.id.milease.mileaseapi.model.dto.create.CreatePlaceDto;
import vn.id.milease.mileaseapi.model.dto.search.PlaceSearchDto;
import vn.id.milease.mileaseapi.model.dto.update.UpdatePlaceDto;
import vn.id.milease.mileaseapi.model.entity.place.Place;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface PlaceService {
    CompletableFuture<PageResult<PlaceDto>> getPlacesAsync(PlaceSearchDto searchDto);

    PageResult<PlaceDto> getPlaces(PlaceSearchDto searchDto);

    PlaceDto addPlace(CreatePlaceDto dto);

    PlaceDto updatePlace(UpdatePlaceDto dto);

    void deletePlace(long id);

    PlaceDto getPlacesById(long id);

    Place getPlace(long id);

    CompletableFuture<Void> updateDisplayIndex();

    public List<PlaceDto> suggestPlaces(PlaceSegment place1, PlaceSegment place2, int placesSize, List<Long> selectedPlaces);
}
