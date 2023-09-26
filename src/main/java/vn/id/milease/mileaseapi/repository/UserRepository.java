package vn.id.milease.mileaseapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.id.milease.mileaseapi.model.entity.user.User;
import vn.id.milease.mileaseapi.repository.custom.UserRepositoryCustom;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    Optional<User> findByEmail(String email);
}
