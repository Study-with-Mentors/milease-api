package vn.id.milease.mileaseapi.repository.custom.impl;

import com.querydsl.core.types.Predicate;
import vn.id.milease.mileaseapi.model.dto.search.PlanSearchDto;
import vn.id.milease.mileaseapi.repository.custom.PlanRepositoryCustom;

public class PlanRepositoryCustomImpl implements PlanRepositoryCustom {
    @Override
    public Predicate prepareSearchPredicate(PlanSearchDto dto) {
        // TODO [Duy, P1] Do this now
        return null;
    }
}
