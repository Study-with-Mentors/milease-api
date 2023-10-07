package vn.id.milease.mileaseapi.service;

import vn.id.milease.mileaseapi.model.dto.TravelerTransactionDto;
import vn.id.milease.mileaseapi.model.dto.create.CreateTravelerTransactionDto;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface TravelerService {
    List<TravelerTransactionDto> getCurrentTravelerTransaction();

    String toPremium(CreateTravelerTransactionDto dto);

    CompletableFuture<Void> updateTravelerStatus(List<Long> ids);
}
