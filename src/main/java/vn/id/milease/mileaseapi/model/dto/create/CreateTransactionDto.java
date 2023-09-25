package vn.id.milease.mileaseapi.model.dto.create;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter
@Setter
public class CreateTransactionDto {
    @Min(1000000)
    private double amount;
    private long placeId;
}
