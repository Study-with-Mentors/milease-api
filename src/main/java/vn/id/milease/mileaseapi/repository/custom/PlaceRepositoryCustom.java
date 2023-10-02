package vn.id.milease.mileaseapi.repository.custom;

import com.querydsl.core.types.Predicate;
import vn.id.milease.mileaseapi.model.dto.search.PlaceSearchDto;
import vn.id.milease.mileaseapi.model.entity.place.Place;

import java.util.Optional;

public interface PlaceRepositoryCustom {
    Predicate prepareSearchPredicate(PlaceSearchDto search);
}
