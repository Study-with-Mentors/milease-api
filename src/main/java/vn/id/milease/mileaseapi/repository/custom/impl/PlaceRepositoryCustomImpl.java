package vn.id.milease.mileaseapi.repository.custom.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.util.StringUtils;
import org.springframework.stereotype.Repository;
import vn.id.milease.mileaseapi.model.dto.search.PlaceSearchDto;
import vn.id.milease.mileaseapi.model.entity.place.QPlace;
import vn.id.milease.mileaseapi.repository.custom.PlaceRepositoryCustom;

@Repository
public class PlaceRepositoryCustomImpl implements PlaceRepositoryCustom {
    @Override
    public Predicate prepareSearchPredicate(PlaceSearchDto search) {
        QPlace place = QPlace.place;
        BooleanBuilder preBuilder = new BooleanBuilder();
        preBuilder.and(place.status.eq(search.getStatus()));
        if(search.getId() > 0) {
            preBuilder.and(place.id.eq(search.getId()));
        }
        if(!StringUtils.isNullOrEmpty(search.getName())) {
            preBuilder.and(place.name.containsIgnoreCase(search.getName()));
        }
        if(search.getDurationFrom() > 0 && search.getDurationTo() > 0 && search.getDurationFrom() > search.getDurationTo()) {
            preBuilder.and(place.averageDuration.between(search.getDurationFrom(), search.getDurationTo()));
        }
        if(search.getTypes() != null &&!search.getTypes().isEmpty()) {
            preBuilder.and(place.type.in(search.getTypes()));
        }
        Predicate pre = preBuilder.getValue();
        if(pre == null)
            pre = Expressions.asBoolean(true).isTrue();
        return pre;
    }
}
