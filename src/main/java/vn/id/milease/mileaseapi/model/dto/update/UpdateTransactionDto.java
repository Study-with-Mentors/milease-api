package vn.id.milease.mileaseapi.model.dto.update;

import lombok.*;
import vn.id.milease.mileaseapi.model.dto.BaseDto;

import javax.validation.constraints.Min;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTransactionDto extends BaseDto {
    private long placeId;
    @Min(1000000)
    private double amount;
}
