package vn.id.milease.mileaseapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TravelerTransactionDto extends BaseDto {
    private LocalDateTime createdAt;
    private float amount;
    private Long oldTransactionId;

}
