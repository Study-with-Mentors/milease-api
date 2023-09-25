package vn.id.milease.mileaseapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import vn.id.milease.mileaseapi.model.entity.place.Place;
import vn.id.milease.mileaseapi.model.entity.place.PlaceStatus;
import vn.id.milease.mileaseapi.repository.custom.PlaceRepositoryCustom;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Long>, QuerydslPredicateExecutor<Place>, PlaceRepositoryCustom {
    List<Place> findAllByStatus(PlaceStatus status);
}
