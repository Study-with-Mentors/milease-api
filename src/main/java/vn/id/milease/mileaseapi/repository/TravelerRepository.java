package vn.id.milease.mileaseapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.id.milease.mileaseapi.model.entity.user.Traveler;
import vn.id.milease.mileaseapi.model.entity.user.TravelerStatus;

import java.util.List;

public interface TravelerRepository extends JpaRepository<Traveler, Long> {
    List<Traveler> findAllByStatus(TravelerStatus status);
}
