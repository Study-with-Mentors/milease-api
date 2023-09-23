package vn.id.milease.mileaseapi.repository.custom.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.util.StringUtils;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Repository;
import vn.id.milease.mileaseapi.model.dto.search.PlaceSearchDto;
import vn.id.milease.mileaseapi.model.entity.QAddress;
import vn.id.milease.mileaseapi.model.entity.place.Place;
import vn.id.milease.mileaseapi.model.entity.place.QPlace;
import vn.id.milease.mileaseapi.repository.custom.PlaceRepositoryCustom;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class PlaceRepositoryCustomImpl implements PlaceRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    private JPAQueryFactory jpaQueryFactory;

    @PostConstruct
    public void postConstruct() {
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }
    @Override
    public Predicate prepareSearchPredicate(PlaceSearchDto search) {
        QPlace place = QPlace.place;
        BooleanBuilder preBuilder = new BooleanBuilder();
        if(!search.getStatuses().isEmpty()) {
            preBuilder.and(place.status.in(search.getStatuses()));
        }
        if(!search.getIds().isEmpty()) {
            preBuilder.and(place.id.in(search.getIds()));
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

    @Override
    public Optional<Place> getPlaceById(long id) {
        QPlace place = QPlace.place;
        QAddress address = QAddress.address;
        var builder = new BooleanBuilder()
                .and(place.id.eq(id)).getValue();
        return Optional.of(jpaQueryFactory.select(place)
                .innerJoin(address)
                .where(builder)
                .fetchJoin()
                .fetch().get(0));
    }
}
