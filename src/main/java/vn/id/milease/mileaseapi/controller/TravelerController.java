package vn.id.milease.mileaseapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.id.milease.mileaseapi.model.dto.TravelerTransactionDto;
import vn.id.milease.mileaseapi.model.dto.create.CreateTravelerTransactionDto;
import vn.id.milease.mileaseapi.service.TravelerService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/travelers")
public class TravelerController {
    private final TravelerService travelerService;

    @PostMapping
    public String updateTravelerStatus() {
        //TODO [Dat, P4]: make this object become a request body when have more than one subscription plan.
        var value = new CreateTravelerTransactionDto();
        value.setAmount(15000);
        return travelerService.toPremium(value);
    }

    @GetMapping
    public ResponseEntity<List<TravelerTransactionDto>> getCurrentTravelerTransactions() {
        return ResponseEntity.ok(travelerService.getCurrentTravelerTransaction());
    }

    @PostMapping("/status")
    public ResponseEntity<Void> updateTravelersStatus(@RequestBody @Valid @NotNull List<Long> ids) {
        travelerService.updateTravelerStatus(ids).thenApply(t -> t).join();
        return ResponseEntity.noContent().build();
    }
}
