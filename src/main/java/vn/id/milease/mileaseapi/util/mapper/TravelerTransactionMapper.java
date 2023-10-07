package vn.id.milease.mileaseapi.util.mapper;

import vn.id.milease.mileaseapi.model.dto.TravelerTransactionDto;
import vn.id.milease.mileaseapi.model.entity.user.TravelerTransaction;

public class TravelerTransactionMapper {
    public static TravelerTransactionDto toDto(TravelerTransaction entity) {
        var result = TravelerTransactionDto.builder()
                .amount(entity.getAmount())
                .createdAt(entity.getCreatedAt())
                .build();
        result.setId(entity.getId());
        if (entity.getOldTransaction() != null)
            result.setOldTransactionId(entity.getOldTransaction().getId());
        return result;
    }
}
