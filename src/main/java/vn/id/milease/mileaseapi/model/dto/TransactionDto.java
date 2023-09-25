package vn.id.milease.mileaseapi.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto extends BaseDto {
    private double amount;
    private LocalDateTime createdAt;
    private PlaceDto place;
}
