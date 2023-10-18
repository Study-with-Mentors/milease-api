package vn.id.milease.mileaseapi.repository;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import vn.id.milease.mileaseapi.model.entity.user.User;
import vn.id.milease.mileaseapi.repository.custom.UserRepositoryCustom;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom, QuerydslPredicateExecutor<User> {
    Optional<User> findByEmail(String email);

    int countByCreatedTimeBetween(LocalDateTime lowerDate, LocalDateTime upperDate);

    @Override
    @EntityGraph(attributePaths = {"traveler"})
    Page<User> findAll(Predicate predicate, Pageable pageable);
}
