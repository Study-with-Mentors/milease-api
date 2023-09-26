package vn.id.milease.mileaseapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import vn.id.milease.mileaseapi.model.entity.place.Place;
import vn.id.milease.mileaseapi.repository.custom.PlaceRepositoryCustom;

public interface PlaceRepository extends JpaRepository<Place, Long>, QuerydslPredicateExecutor<Place>, PlaceRepositoryCustom {
}
