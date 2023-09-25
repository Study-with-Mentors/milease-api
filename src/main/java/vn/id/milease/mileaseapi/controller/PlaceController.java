package vn.id.milease.mileaseapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.id.milease.mileaseapi.model.dto.PageResult;
import vn.id.milease.mileaseapi.model.dto.PlaceDto;
import vn.id.milease.mileaseapi.model.dto.create.CreatePlaceDto;
import vn.id.milease.mileaseapi.model.dto.search.PlaceSearchDto;
import vn.id.milease.mileaseapi.model.dto.update.UpdatePlaceDto;
import vn.id.milease.mileaseapi.model.entity.place.Place;
import vn.id.milease.mileaseapi.model.entity.place.PlaceStatus;
import vn.id.milease.mileaseapi.model.entity.place.PlaceType;
import vn.id.milease.mileaseapi.service.PlaceService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/places")
@RequiredArgsConstructor
public class PlaceController {
    private final PlaceService placeService;

    @GetMapping
    public ResponseEntity<PageResult<PlaceDto>> searchPlaces(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "DESC") Sort.Direction direction,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) List<Long> ids,
            @RequestParam(required = false) List<PlaceType> types,
            @RequestParam(required = false) List<PlaceStatus> statuses,
            @RequestParam(required = false) PlaceSearchDto.PlaceProperty orderBy,
            @RequestParam(defaultValue = "0") float durationFrom,
            @RequestParam(defaultValue = "0") float durationTo
    ) {
        var searchDto = PlaceSearchDto.builder()
                .ids(ids)
                .durationFrom(durationFrom)
                .durationTo(durationTo)
                .name(name)
                .types(types)
                .orderBy(orderBy != null ? orderBy : PlaceSearchDto.PlaceProperty.DISPLAY_INDEX)
                .statuses(statuses)
                .build();
        searchDto.setDirection(direction);
        searchDto.setPage(page);
        searchDto.setPageSize(pageSize);
        return new ResponseEntity<>(
                placeService.getPlacesAsync(searchDto).thenApply(r -> r).join(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaceDto> getPlacesById(@PathVariable long id) {
        return new ResponseEntity<>(placeService.getPlacesById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> addPlace(@RequestBody CreatePlaceDto dto, HttpServletResponse response) {
        PlaceDto result = placeService.addPlace(dto);
        String location = "/api/places/" + result.getId();
        response.addHeader("location", location);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlaceDto> updatePlace(@PathVariable(name = "id") long id, @RequestBody UpdatePlaceDto dto) {
        dto.setId(id);
        PlaceDto updated = placeService.updatePlace(dto);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlace(@PathVariable(name = "id") long id) {
        placeService.deletePlace(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
