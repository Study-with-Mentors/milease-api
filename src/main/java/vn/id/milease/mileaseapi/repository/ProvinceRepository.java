package vn.id.milease.mileaseapi.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import vn.id.milease.mileaseapi.model.entity.administrativeunit.Province;
import vn.id.milease.mileaseapi.repository.custom.ProvinceRepositoryCustom;

import java.util.List;

public interface ProvinceRepository extends JpaRepository<Province, String>, QuerydslPredicateExecutor<Province>, ProvinceRepositoryCustom {
}
