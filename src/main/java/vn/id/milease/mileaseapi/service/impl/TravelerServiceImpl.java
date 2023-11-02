package vn.id.milease.mileaseapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import vn.id.milease.mileaseapi.configuration.AppConstant;
import vn.id.milease.mileaseapi.model.dto.TravelerTransactionDto;
import vn.id.milease.mileaseapi.model.dto.create.CreateTravelerTransactionDto;
import vn.id.milease.mileaseapi.model.entity.user.Traveler;
import vn.id.milease.mileaseapi.model.entity.user.TravelerStatus;
import vn.id.milease.mileaseapi.model.entity.user.TravelerTransaction;
import vn.id.milease.mileaseapi.model.entity.user.UserRole;
import vn.id.milease.mileaseapi.model.exception.UnauthorizedException;
import vn.id.milease.mileaseapi.repository.TravelerRepository;
import vn.id.milease.mileaseapi.repository.TravelerTransactionRepository;
import vn.id.milease.mileaseapi.service.TravelerService;
import vn.id.milease.mileaseapi.service.UserService;
import vn.id.milease.mileaseapi.service.util.ServiceUtil;
import vn.id.milease.mileaseapi.util.mapper.TravelerTransactionMapper;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Transactional
@Service
@RequiredArgsConstructor
public class TravelerServiceImpl implements TravelerService {
    private final UserService userService;
    private final TravelerTransactionRepository transactionRepository;
    private final TravelerRepository travelerRepository;
    @Value("${app.bank.id}")
    private String bankId;
    @Value("${app.bank.number}")
    private String bankNumber;

    @Override
    public List<TravelerTransactionDto> getCurrentTravelerTransaction() {
        var traveler = getCurrentTraveler();
        if (traveler.getUser() != null && traveler.getUser().getRole() == UserRole.ADMIN) {
            return transactionRepository.findAll().stream().map(TravelerTransactionMapper::toDto).toList();
        }
        var transactions = transactionRepository.findAllByTraveler(traveler);
        return transactions.stream().map(TravelerTransactionMapper::toDto).toList();
    }

    @Override
    public String toPremium(CreateTravelerTransactionDto dto) {
        var traveler = getCurrentTraveler();
        var entityToAdd = TravelerTransaction.builder()
                .traveler(traveler)
                .createdAt(LocalDateTime.now(AppConstant.VN_ZONE_ID))
                .amount(dto.getAmount())
                .build();
        if (traveler.getLatestTransaction() != null) entityToAdd.setOldTransaction(traveler.getLatestTransaction());
        traveler.setLatestTransaction(entityToAdd);
        entityToAdd = transactionRepository.save(entityToAdd);
        travelerRepository.save(traveler);

        return ServiceUtil.generatePaymentQr(bankId, bankNumber, Math.round(entityToAdd.getAmount()), "Milease TravelerID: " + traveler.getId() + ", Transaction id: " + entityToAdd.getId());
    }

    @Override
    @Async
    public CompletableFuture<Void> updateTravelerStatus(List<Long> ids) {
        if (ids == null || ids.isEmpty()) return updateTravelerStatus();
        updateTravelerStatus((ArrayList<Long>) ids);
        return null;
    }

    private void updateTravelerStatus(ArrayList<Long> ids) {
        var travelers = travelerRepository.findAllById(ids);
        for (var traveler : travelers) {
            if (traveler.getStatus() != null && traveler.getStatus() != TravelerStatus.PREMIUM)
                traveler.setStatus(TravelerStatus.PREMIUM);
            var newExpiredDay = LocalDateTime.now(AppConstant.VN_ZONE_ID).plusDays(30);
            if (traveler.getPremiumExpiredDate() != null)
                newExpiredDay = traveler.getPremiumExpiredDate().plusDays(30);
            traveler.setPremiumExpiredDate(newExpiredDay);
        }
        travelerRepository.saveAll(travelers);
    }

    private CompletableFuture<Void> updateTravelerStatus() {
        return CompletableFuture.supplyAsync(() -> {
            var travelers = travelerRepository.findAll();
            long numberOfUpdatedUsers = 0;
            for (var traveler : travelers) {
                if (traveler.getPremiumExpiredDate() != null && traveler.getPremiumExpiredDate().isAfter(LocalDateTime.now(AppConstant.VN_ZONE_ID)) && (traveler.getStatus() == TravelerStatus.PREMIUM)) {
                    numberOfUpdatedUsers++;
                    traveler.setStatus(TravelerStatus.NORMAL);
                }
                if (traveler.getPremiumExpiredDate() != null && traveler.getPremiumExpiredDate().isBefore(LocalDateTime.now(AppConstant.VN_ZONE_ID)) && (traveler.getStatus() == TravelerStatus.NORMAL)) {
                    numberOfUpdatedUsers++;
                    traveler.setStatus(TravelerStatus.PREMIUM);
                }
            }
            if (numberOfUpdatedUsers > 0)
                travelerRepository.saveAll(travelers);
            return null;
        });
    }

    private Traveler getCurrentTraveler() {
        var user = userService.getCurrentUser();
        return travelerRepository.findById(user.getId())
                .orElseThrow(UnauthorizedException::new);
    }
}
