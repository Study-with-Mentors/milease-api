package vn.id.milease.mileaseapi.service;

import vn.id.milease.mileaseapi.model.dto.TransactionDto;
import vn.id.milease.mileaseapi.model.dto.create.CreateTransactionDto;
import vn.id.milease.mileaseapi.model.dto.update.UpdateTransactionDto;

import java.util.concurrent.CompletableFuture;

public interface TransactionService {

    CompletableFuture<Long> createTransactionAsync(CreateTransactionDto dto);

    CompletableFuture<TransactionDto> getTransactionByIdAsync(long id);

    CompletableFuture<Long> updateTransaction(UpdateTransactionDto dto);
}
