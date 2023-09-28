package vn.id.milease.mileaseapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import vn.id.milease.mileaseapi.model.dto.TransactionDto;
import vn.id.milease.mileaseapi.model.dto.create.CreateTransactionDto;
import vn.id.milease.mileaseapi.service.TransactionService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/businesses/transactions")
public class BusinessTransactionController {
    private final TransactionService transactionService;

        @GetMapping("/{id}")
        public ResponseEntity<TransactionDto> getTransactionById(@PathVariable long id) {
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
