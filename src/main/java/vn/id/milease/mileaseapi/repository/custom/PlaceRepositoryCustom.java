package vn.id.milease.mileaseapi.repository.custom;

import com.querydsl.core.types.Predicate;
import vn.id.milease.mileaseapi.model.dto.search.PlaceSearchDto;

public interface PlaceRepositoryCustom {
    Predicate prepareSearchPredicate(PlaceSearchDto search);
}
