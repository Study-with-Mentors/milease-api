package vn.id.milease.mileaseapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.id.milease.mileaseapi.model.dto.PageResult;
import vn.id.milease.mileaseapi.model.dto.PlaceDto;
import vn.id.milease.mileaseapi.model.dto.SuggestDto;
import vn.id.milease.mileaseapi.model.dto.create.CreatePlaceDto;
import vn.id.milease.mileaseapi.model.dto.search.PlaceSearchDto;
import vn.id.milease.mileaseapi.model.dto.update.UpdatePlaceDto;
import vn.id.milease.mileaseapi.service.PlaceService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/places")
@RequiredArgsConstructor
public class PlaceController {
    private final PlaceService placeService;

    @GetMapping
    public ResponseEntity<PageResult<PlaceDto>> searchPlaces(@Valid PlaceSearchDto searchDto) {
        return new ResponseEntity<>(
                placeService.getPlacesAsync(searchDto).thenApply(r -> r).join(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaceDto> getPlacesById(@PathVariable long id) {
        return new ResponseEntity<>(placeService.getPlacesById(id), HttpStatus.OK);
    }

    @PostMapping("/suggest")
    public ResponseEntity<List<PlaceDto>> suggestPlaces(@RequestBody @Valid SuggestDto suggestDto) {
        return ResponseEntity.ok(placeService.suggestPlaces(suggestDto.getPlace1(), suggestDto.getPlace2(), suggestDto.getPlacesSize(), suggestDto.getSelectedPlaces()));
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
