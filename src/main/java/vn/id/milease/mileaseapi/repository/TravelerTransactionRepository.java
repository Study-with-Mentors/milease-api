package vn.id.milease.mileaseapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.id.milease.mileaseapi.model.entity.user.Traveler;
import vn.id.milease.mileaseapi.model.entity.user.TravelerTransaction;

import java.util.List;

public interface TravelerTransactionRepository extends JpaRepository<TravelerTransaction, Long> {
    List<TravelerTransaction> findAllByTraveler(Traveler traveler);
}
