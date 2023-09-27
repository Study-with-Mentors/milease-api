package vn.id.milease.mileaseapi.service.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import vn.id.milease.mileaseapi.model.dto.PageResult;
import vn.id.milease.mileaseapi.model.dto.search.BaseSearchDto;
import vn.id.milease.mileaseapi.model.entity.place.Place;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.function.Function;

public class ServiceUtil {
    public static PageRequest preparePageRequest(BaseSearchDto searchDto) {
        return PageRequest.of(
                searchDto.getPage(),
                searchDto.getPageSize(),
                Sort.by(searchDto.getDirection(), searchDto.getOrderBy()));
    }

    public static <Entity, Dto> PageResult<Dto> toPageResult(Page<Entity> dtoPage, Function<Entity, Dto> mapperFunc, BaseSearchDto searchDto) {
        PageResult<Dto> result = new PageResult<>();

        result.setValues(dtoPage.get()
                .map(mapperFunc)
                .toList());
        result.setTotalPages(dtoPage.getTotalPages());
        result.setTotalCount(dtoPage.getTotalElements());
        result.setCurrentPage(searchDto.getPage());
        return result;
    }

    public static long calculateAmountOfDisplayIndex(Place place, LocalDateTime currentTime, long allowTimeIntervalToCalBonus) {
        double totalAmount = 0;
        long timeInterval;
        long bonus = 0;
        if (place.getLastestTransaction() != null) {
            timeInterval = Duration.between(currentTime, place.getLastestTransaction().getCreatedAt()).getSeconds();
            var currentTransaction = place.getLastestTransaction();
            totalAmount += currentTransaction.getAmount();
            while (true) {
                currentTransaction = currentTransaction.getOldTransaction();
                if (currentTransaction == null)
                    break;
                totalAmount += currentTransaction.getAmount();
                timeInterval = Duration.between(currentTime, currentTransaction.getCreatedAt()).getSeconds();
                if (timeInterval > allowTimeIntervalToCalBonus)
                    break;
            }
            //TODO [Dat, P4]: Calculating bonus base on BR's formula
            bonus = (long) Math.ceil(totalAmount) * 10;
        } else
            timeInterval = Math.abs(Duration.between(currentTime, place.getCreatedAt()).getSeconds());
        return -timeInterval + bonus;
    }
}
