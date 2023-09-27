package vn.id.milease.mileaseapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.id.milease.mileaseapi.model.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
