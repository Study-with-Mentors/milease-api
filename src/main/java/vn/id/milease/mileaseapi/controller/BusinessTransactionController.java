package vn.id.milease.mileaseapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.id.milease.mileaseapi.model.dto.TransactionDto;
import vn.id.milease.mileaseapi.model.dto.create.CreateTransactionDto;
import vn.id.milease.mileaseapi.service.TransactionService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/business/transactions")
public class BusinessTransactionController {
    private final TransactionService transactionService;

        @GetMapping("{id}")
        public ResponseEntity<TransactionDto> getTransactionById(@RequestParam long id) {
            return new ResponseEntity<>(
                    transactionService.getTransactionByIdAsync(id).thenApply(r -> r).join(),
                    HttpStatus.OK);
        }
        @PostMapping
        public ResponseEntity<Void> createNewTransaction(@RequestBody CreateTransactionDto dto, HttpServletResponse response) {
            transactionService.createTransactionAsync(dto).thenApply(r -> {
                String location = "/api/business/transactions/" + r;
                response.addHeader("location", location);
                return r;
            }).join();
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
}
