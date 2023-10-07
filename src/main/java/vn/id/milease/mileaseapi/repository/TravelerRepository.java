package vn.id.milease.mileaseapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.id.milease.mileaseapi.model.entity.user.Traveler;

public interface TravelerRepository extends JpaRepository<Traveler, Long> {
}
