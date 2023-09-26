package vn.id.milease.mileaseapi.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import vn.id.milease.mileaseapi.configuration.AppConstant;
import vn.id.milease.mileaseapi.model.dto.TransactionDto;
import vn.id.milease.mileaseapi.model.dto.create.CreateTransactionDto;
import vn.id.milease.mileaseapi.model.dto.update.UpdateTransactionDto;
import vn.id.milease.mileaseapi.model.entity.Transaction;
import vn.id.milease.mileaseapi.model.entity.place.Place;
import vn.id.milease.mileaseapi.model.exception.NotFoundException;
import vn.id.milease.mileaseapi.repository.PlaceRepository;
import vn.id.milease.mileaseapi.repository.TransactionRepository;
import vn.id.milease.mileaseapi.service.TransactionService;
import vn.id.milease.mileaseapi.service.util.ServiceUtil;
import vn.id.milease.mileaseapi.util.mapper.TransactionMapper;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final PlaceRepository placeRepository;
    private final TransactionMapper transactionMapper;

    @Async
    @Override
    public CompletableFuture<Long> createTransactionAsync(CreateTransactionDto dto) {
        return CompletableFuture.supplyAsync(() -> {
            var existedPlace = placeRepository.findById(dto.getPlaceId())
                    .orElseThrow(() -> new NotFoundException(Place.class, dto.getPlaceId()));
            var entityToAdd = transactionMapper.toEntity(dto);
            entityToAdd.setCreatedAt(LocalDateTime.now(AppConstant.VN_ZONE_ID));
            entityToAdd.setPlace(existedPlace);
            if (existedPlace.getLastestTransaction() != null)
                entityToAdd.setOldTransaction(existedPlace.getLastestTransaction());
            existedPlace.setLastestTransaction(entityToAdd);
            entityToAdd = transactionRepository.save(entityToAdd);
            placeRepository.save(existedPlace);
            return entityToAdd.getId();
        });
    }

    @Async
    @Override
    public CompletableFuture<TransactionDto> getTransactionByIdAsync(long id) {
        return CompletableFuture.supplyAsync(() -> transactionMapper.toDto(getTransactionById(id)));
    }

    @Override
    public CompletableFuture<Long> updateTransaction(UpdateTransactionDto dto) {
        return CompletableFuture.supplyAsync(() -> {
            var existed = placeRepository.findById(dto.getId())
                    .orElseThrow(() -> new NotFoundException(Place.class, dto.getPlaceId()));
            var entityToUpdate = transactionRepository.findById(dto.getId())
                    .orElseThrow(() -> new NotFoundException(Transaction.class, dto.getId()));
            transactionMapper.toEntity(dto, entityToUpdate);
            transactionRepository.save(entityToUpdate);
            ServiceUtil.calculateAmountOfDisplayIndex(existed, LocalDateTime.now(AppConstant.VN_ZONE_ID), AppConstant.ALLOW_TIME_INTERVAL_TO_CAL_BONUS);
            placeRepository.save(existed);
            return entityToUpdate.getId();
        });
    }

    private Transaction getTransactionById(long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Transaction.class, id));
    }
}
