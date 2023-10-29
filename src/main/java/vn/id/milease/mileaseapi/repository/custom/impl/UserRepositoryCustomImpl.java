package vn.id.milease.mileaseapi.repository.custom.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import vn.id.milease.mileaseapi.model.dto.search.UserSearchDto;
import vn.id.milease.mileaseapi.model.entity.user.QTraveler;
import vn.id.milease.mileaseapi.model.entity.user.QUser;
import vn.id.milease.mileaseapi.model.entity.user.TravelerStatus;
import vn.id.milease.mileaseapi.repository.custom.UserRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

@Repository
public class UserRepositoryCustomImpl implements UserRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public int countPremiumUser(LocalDateTime lowerDate, LocalDateTime upperTime) {
        QTraveler traveler = QTraveler.traveler;
        BooleanBuilder builder = new BooleanBuilder();
        LocalDateTime now = LocalDateTime.now();
        builder.and(traveler.status.eq(TravelerStatus.PREMIUM));
        builder.and(traveler.premiumExpiredDate.after(now));
        if (lowerDate != null) {
            builder.and(traveler.premiumExpiredDate.after(lowerDate));
        }
        if (upperTime != null) {
            builder.and(traveler.premiumExpiredDate.before(upperTime));
        }
        JPAQueryFactory query = new JPAQueryFactory(em);

        return (int) query.selectFrom(traveler)
                .where(builder.getValue())
                .stream().count();
    }

    @Override
    public Predicate prepareSearchPredicate(UserSearchDto dto) {
        QUser user = QUser.user;
        BooleanBuilder builder = new BooleanBuilder();
        if (dto.getLowerDate() != null) {
            builder.and(user.createdTime.after(dto.getLowerDate()));
        }
        if (dto.getUpperDate() != null) {
            builder.and(user.createdTime.before(dto.getUpperDate()));
        }
        Predicate predicate = builder.getValue();
        if (predicate == null) {
            predicate = Expressions.asBoolean(true).isTrue();
        }
        return predicate;
    }
}
