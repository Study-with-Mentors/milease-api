package vn.id.milease.mileaseapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import vn.id.milease.mileaseapi.model.entity.step.Step;

import java.util.List;

public interface StepRepository extends JpaRepository<Step, Long>, QuerydslPredicateExecutor<Step> {
    List<Step> findByPlanId(long planId);
}
