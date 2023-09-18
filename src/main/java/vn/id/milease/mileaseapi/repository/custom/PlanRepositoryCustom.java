package vn.id.milease.mileaseapi.repository.custom;

import com.querydsl.core.types.Predicate;
import vn.id.milease.mileaseapi.model.dto.search.PlanSearchDto;

public interface PlanRepositoryCustom {
    Predicate prepareSearchPredicate(PlanSearchDto dto);
}
