package vn.id.milease.mileaseapi.model.dto.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTravelerTransactionDto {
    @Min(15000)
    @Max(15000)
    private float amount;
}
