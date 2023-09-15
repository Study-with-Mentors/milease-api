package vn.id.milease.mileaseapi.model.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {
    private int totalPages;
    private long totalCount;
    private List<T> values;
}
