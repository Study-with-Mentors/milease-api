package vn.id.milease.mileaseapi.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.id.milease.mileaseapi.model.dto.PageResult;
import vn.id.milease.mileaseapi.model.dto.TravelerDto;
import vn.id.milease.mileaseapi.model.dto.search.UserSearchDto;
import vn.id.milease.mileaseapi.model.entity.user.User;
import vn.id.milease.mileaseapi.model.exception.UnauthorizedException;
import vn.id.milease.mileaseapi.repository.UserRepository;
import vn.id.milease.mileaseapi.service.UserService;
import vn.id.milease.mileaseapi.service.util.ServiceUtil;
import vn.id.milease.mileaseapi.util.ApplicationMapper;

import javax.transaction.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ApplicationMapper mapper;

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
    }

    @Override
    public User getCurrentUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findById(user.getId())
                .orElseThrow(UnauthorizedException::new);
    }

    @Override
    public PageResult<TravelerDto> getUsers(UserSearchDto searchDto) {
        var predicate = userRepository.prepareSearchPredicate(searchDto);
        PageRequest pageRequest = ServiceUtil.preparePageRequest(searchDto);
        Page<User> users = userRepository.findAll(predicate, pageRequest);
        return ServiceUtil.toPageResult(users, mapper.getUserMapper()::toDto, searchDto);
    }

    @Override
    public int countUser(UserSearchDto searchDto) {
        return userRepository.countByCreatedTimeBetween(searchDto.getLowerDate(), searchDto.getUpperDate());
    }

    @Override
    public int countPremiumUser(UserSearchDto searchDto) {
        return userRepository.countPremiumUser(searchDto.getLowerDate(), searchDto.getUpperDate());
    }
}
