package vn.id.milease.mileaseapi.repository.custom;

import com.querydsl.core.types.Predicate;
import vn.id.milease.mileaseapi.model.dto.search.PlanSearchDto;
import vn.id.milease.mileaseapi.model.entity.plan.PlanIdOnly;

public interface PlanRepositoryCustom {
    Predicate prepareSearchPredicate(PlanSearchDto dto, Long userId);

    void updatePlan(PlanIdOnly planIdOnly);
}
