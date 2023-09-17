package vn.id.milease.mileaseapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.id.milease.mileaseapi.model.dto.search.PlaceSearchDto;
import vn.id.milease.mileaseapi.model.entity.place.PlaceStatus;
import vn.id.milease.mileaseapi.model.entity.place.PlaceType;

@RestController
@RequestMapping("/enums")
public class EnumController {
    @GetMapping("/places/statuses")
    public PlaceStatus[] getPlaceStatuses() {
        return PlaceStatus.values();
    }

    @GetMapping("/places/types")
    public PlaceType[] getPlaceTypes() {
        return PlaceType.values();
    }

    @GetMapping("/places/orderby")
    public PlaceSearchDto.OrderBy[] getPlaceOrdering() {
        return PlaceSearchDto.OrderBy.values();
    }
}
