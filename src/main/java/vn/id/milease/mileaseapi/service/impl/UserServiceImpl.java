package vn.id.milease.mileaseapi.service.impl;

import org.springframework.stereotype.Service;
import vn.id.milease.mileaseapi.model.entity.user.User;
import vn.id.milease.mileaseapi.service.UserService;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Override
    public User getCurrentUser() {
        // TODO [Khanh, P1] Implement after having jwt filter working
        return null;
    }
}
