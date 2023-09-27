package vn.id.milease.mileaseapi.repository.custom.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAUpdateClause;
import org.springframework.stereotype.Repository;
import vn.id.milease.mileaseapi.model.dto.search.PlanSearchDto;
import vn.id.milease.mileaseapi.model.entity.plan.PlanIdOnly;
import vn.id.milease.mileaseapi.model.entity.plan.QPlan;
import vn.id.milease.mileaseapi.repository.custom.PlanRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class PlanRepositoryCustomImpl implements PlanRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Predicate prepareSearchPredicate(PlanSearchDto dto, Long userId) {
        QPlan plan = QPlan.plan;
        BooleanBuilder builder = new BooleanBuilder();
        if (userId != null) {
            builder.and(plan.user.id.eq(userId));
        }
        if (dto.getName() != null) {
            builder.and(plan.name.containsIgnoreCase(dto.getName()));
        }
        if (dto.getLowerDate() != null) {
            builder.and(plan.start.after(dto.getLowerDate()));
        }
        if (dto.getUpperDate() != null) {
            builder.and(plan.end.before(dto.getUpperDate()));
        }
        Predicate predicate = builder.getValue();
        if (predicate == null) {
            predicate = Expressions.asBoolean(true).isTrue();
        }
        return predicate;
    }

    @Override
    public void updatePlan(PlanIdOnly planIdOnly) {
        QPlan plan = QPlan.plan;
        JPAUpdateClause updateClause = new JPAUpdateClause(em, plan);
        updateClause.set(plan.firstStep.id, planIdOnly.getFirstStepId())
                .where(plan.id.eq(planIdOnly.getId()))
                .execute();
    }
}
