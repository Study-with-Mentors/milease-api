package vn.id.milease.mileaseapi.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import vn.id.milease.mileaseapi.model.dto.PageResult;
import vn.id.milease.mileaseapi.model.dto.TravelerDto;
import vn.id.milease.mileaseapi.model.dto.search.UserSearchDto;
import vn.id.milease.mileaseapi.model.entity.user.User;

public interface UserService extends UserDetailsService {
    User getCurrentUser();

    PageResult<TravelerDto> getUsers(UserSearchDto searchDto);

    int countUser(UserSearchDto searchDto);

    int countPremiumUser(UserSearchDto searchDto);
}
