package vn.id.milease.mileaseapi.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.id.milease.mileaseapi.model.dto.PageResult;
import vn.id.milease.mileaseapi.model.dto.PlaceDto;
import vn.id.milease.mileaseapi.model.dto.create.CreatePlaceDto;
import vn.id.milease.mileaseapi.model.dto.search.PlaceSearchDto;
import vn.id.milease.mileaseapi.model.dto.update.UpdatePlaceDto;
import vn.id.milease.mileaseapi.service.PlaceService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/places")
@RequiredArgsConstructor
public class PlaceController {
    private final PlaceService placeService;
    @GetMapping
    public ResponseEntity<PageResult<PlaceDto>> searchPlaces(PlaceSearchDto searchDto) {
        return new ResponseEntity<>(placeService.getPlaces(searchDto), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> addPlace(@RequestBody CreatePlaceDto dto, HttpServletResponse response) {
        PlaceDto result = placeService.addPlace(dto);
        String location = "/api/places/" + result.getId();
        response.addHeader("location", location);
        return  ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlaceDto> updatePlace(@RequestParam(name = "id") long id, @RequestBody UpdatePlaceDto dto) {
        dto.setId(id);
        PlaceDto updated = placeService.updatePlace(dto);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    //TODO: create soft delete
}
