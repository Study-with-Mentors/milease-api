package vn.id.milease.mileaseapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import vn.id.milease.mileaseapi.model.entity.user.User;
import vn.id.milease.mileaseapi.repository.custom.UserRepositoryCustom;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom, QuerydslPredicateExecutor<User> {
    Optional<User> findByEmail(String email);

    int countByCreatedTimeBetween(LocalDateTime lowerDate, LocalDateTime upperDate);
}
