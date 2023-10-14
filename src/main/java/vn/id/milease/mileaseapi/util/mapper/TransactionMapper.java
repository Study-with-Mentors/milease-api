package vn.id.milease.mileaseapi.util.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vn.id.milease.mileaseapi.model.dto.TransactionDto;
import vn.id.milease.mileaseapi.model.dto.create.CreateTransactionDto;
import vn.id.milease.mileaseapi.model.dto.update.UpdateTransactionDto;
import vn.id.milease.mileaseapi.model.entity.Transaction;

@Component
@RequiredArgsConstructor
public class TransactionMapper implements Mapper<Transaction, TransactionDto, CreateTransactionDto, UpdateTransactionDto> {
    private final PlaceMapper placeMapper;

    @Override
    public Transaction toEntity(CreateTransactionDto dto) {
        return Transaction.builder()
                .amount(dto.getAmount())
                .build();
    }

    @Override
    public void toEntity(UpdateTransactionDto dto, Transaction entity) {
        entity.setAmount(dto.getAmount());
    }

    @Override
    public TransactionDto toDto(Transaction entity) {
        var resultBuilder = TransactionDto.builder()
                .amount(entity.getAmount())
                .createdAt(entity.getCreatedAt());
        if (entity.getPlace() != null)
            resultBuilder.place(placeMapper.toDto(entity.getPlace()));
        var result = resultBuilder.build();
        result.setId(entity.getId());
        return result;
    }
}
