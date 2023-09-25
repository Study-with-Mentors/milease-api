package vn.id.milease.mileaseapi.util.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vn.id.milease.mileaseapi.model.dto.TransactionDto;
import vn.id.milease.mileaseapi.model.dto.create.CreateTransactionDto;
import vn.id.milease.mileaseapi.model.dto.update.UpdateTransactionDto;
import vn.id.milease.mileaseapi.model.entity.Transaction;
import vn.id.milease.mileaseapi.model.entity.place.Place;

@Component
@RequiredArgsConstructor
public class TransactionMapper {
    private final PlaceMapper placeMapper;
    public Transaction toEntity(CreateTransactionDto dto) {
        return Transaction.builder()
                .amount(dto.getAmount())
                .build();
    }

    public void toEntity(UpdateTransactionDto o, Transaction o2) {
        o2.setAmount(o.getAmount());
    }

    public TransactionDto toDto(Transaction entity) {
        var resultBuilder = TransactionDto.builder()
                .amount(entity.getAmount())
                .createdAt(entity.getCreatedAt());
        if(entity.getPlace() != null)
            resultBuilder.place(placeMapper.toDto(entity.getPlace()));
        var result = resultBuilder.build();
        result.setId(entity.getId());
        return result;
    }
}
