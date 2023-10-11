package vn.id.milease.mileaseapi.repository.custom;

import com.querydsl.core.types.Predicate;
import vn.id.milease.mileaseapi.model.dto.search.UserSearchDto;

import java.time.LocalDateTime;

public interface UserRepositoryCustom {
    int countPremiumUser(LocalDateTime lowerDate, LocalDateTime upperTime);

    Predicate prepareSearchPredicate(UserSearchDto searchDto);
}
