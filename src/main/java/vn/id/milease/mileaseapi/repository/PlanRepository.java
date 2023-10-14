package vn.id.milease.mileaseapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import vn.id.milease.mileaseapi.model.entity.plan.Plan;
import vn.id.milease.mileaseapi.model.entity.plan.PlanIdOnly;
import vn.id.milease.mileaseapi.repository.custom.PlanRepositoryCustom;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PlanRepository extends JpaRepository<Plan, Long>, QuerydslPredicateExecutor<Plan>, PlanRepositoryCustom {
    @Query("select new vn.id.milease.mileaseapi.model.entity.plan.PlanIdOnly(p.id, p.user.id, p.firstStep.id) from Plan p where p.id = ?1")
    Optional<PlanIdOnly> findIdOnlyById(long id);

    int countByStartBetween(LocalDateTime lowerDate, LocalDateTime upperDate);
}
