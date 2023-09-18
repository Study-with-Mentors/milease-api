package vn.id.milease.mileaseapi.service.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import vn.id.milease.mileaseapi.model.dto.PageResult;
import vn.id.milease.mileaseapi.model.dto.search.BaseSearchDto;

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
}
