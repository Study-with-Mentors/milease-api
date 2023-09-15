package vn.id.milease.mileaseapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.id.milease.mileaseapi.model.dto.search.PlaceSearchDto;
import vn.id.milease.mileaseapi.model.entity.place.PlaceStatus;
import vn.id.milease.mileaseapi.model.entity.place.PlaceType;

import java.util.List;

@RestController
@RequestMapping("/enums")
public class EnumController {
    @GetMapping("/place-status")
    public ResponseEntity<PlaceStatus[]> getPlaceStatuses() {
        return new ResponseEntity<>(PlaceStatus.values(), HttpStatus.OK);
    }

    @GetMapping("/place-type")
    public ResponseEntity<PlaceType[]> getPlaceTypes() {
        return new ResponseEntity<>(PlaceType.values(), HttpStatus.OK);
    }

    @GetMapping("/place-orderby")
    public ResponseEntity<PlaceSearchDto.OrderBy[]> getPlaceOrdering() {
        return new ResponseEntity<>(PlaceSearchDto.OrderBy.values(), HttpStatus.OK);
    }
}
