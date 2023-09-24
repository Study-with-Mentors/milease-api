package vn.id.milease.mileaseapi.repository.custom.impl;

import com.querydsl.jpa.impl.JPAUpdateClause;
import vn.id.milease.mileaseapi.model.entity.step.QStep;
import vn.id.milease.mileaseapi.model.entity.step.StepIdOnly;
import vn.id.milease.mileaseapi.repository.custom.StepRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class StepRepositoryCustomImpl implements StepRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void updateStepPosition(StepIdOnly stepId) {
        QStep step = QStep.step;
        JPAUpdateClause jpaUpdateClause = new JPAUpdateClause(em, QStep.step);
        jpaUpdateClause.set(step.nextStep.id, stepId.getNextStepId())
                .set(step.previousStep.id, stepId.getPreviousStepId())
                .set(step.plan.id, stepId.getPlanId())
                .where(step.id.eq(stepId.getId()))
                .execute();
    }

    @Override
    public void updateNextStepById(Long id, Long nextStepId) {
        QStep step = QStep.step;
        JPAUpdateClause jpaUpdateClause = new JPAUpdateClause(em, QStep.step);
        jpaUpdateClause.set(step.nextStep.id, nextStepId)
                .where(step.id.eq(id))
                .execute();
    }

    @Override
    public void updatePreviousStepById(Long id, Long previousStepId) {

        QStep step = QStep.step;
        JPAUpdateClause jpaUpdateClause = new JPAUpdateClause(em, QStep.step);
        jpaUpdateClause.set(step.previousStep.id, previousStepId)
                .where(step.id.eq(id))
                .execute();
    }
}
