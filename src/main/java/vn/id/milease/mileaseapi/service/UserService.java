package vn.id.milease.mileaseapi.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import vn.id.milease.mileaseapi.model.entity.user.User;

public interface UserService extends UserDetailsService {
    User getCurrentUser();
}
