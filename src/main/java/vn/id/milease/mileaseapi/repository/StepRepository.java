package vn.id.milease.mileaseapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import vn.id.milease.mileaseapi.model.entity.step.Step;
import vn.id.milease.mileaseapi.model.entity.step.StepIdOnly;
import vn.id.milease.mileaseapi.repository.custom.StepRepositoryCustom;

import java.util.List;
import java.util.Optional;

public interface StepRepository extends JpaRepository<Step, Long>, QuerydslPredicateExecutor<Step>, StepRepositoryCustom {
    List<Step> findByPlanId(long planId);

    @Query("select new vn.id.milease.mileaseapi.model.entity.step.StepIdOnly(s.id, s.previousStep.id, s.nextStep.id, s.plan.id) from Step s where s.id = ?1")
    Optional<StepIdOnly> findIdOnlyById(long id);

    // TODO [Duy, P3] refactor so that the select sql will not join nextStep and prevStep
}
