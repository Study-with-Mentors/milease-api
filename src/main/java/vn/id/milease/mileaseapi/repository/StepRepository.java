package vn.id.milease.mileaseapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.id.milease.mileaseapi.model.entity.step.Step;

public interface StepRepository extends JpaRepository<Step, Long> {
}
