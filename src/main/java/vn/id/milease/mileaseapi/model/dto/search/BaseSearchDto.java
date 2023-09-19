package vn.id.milease.mileaseapi.model.dto.search;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public abstract class BaseSearchDto {
    private Sort.Direction direction = Sort.Direction.ASC;
    private int page = 0;
    private int pageSize = 10;

    public abstract String getOrderBy();
}
