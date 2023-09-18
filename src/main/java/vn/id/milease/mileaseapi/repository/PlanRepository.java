package vn.id.milease.mileaseapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import vn.id.milease.mileaseapi.model.entity.plan.Plan;
import vn.id.milease.mileaseapi.repository.custom.PlanRepositoryCustom;

public interface PlanRepository extends JpaRepository<Plan, Long>, QuerydslPredicateExecutor<Plan>, PlanRepositoryCustom {
}
