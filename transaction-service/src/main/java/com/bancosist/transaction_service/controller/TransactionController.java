package com.bancosist.transaction_service.controller;

import com.bancosist.transaction_service.dto.TransactionRequest;
import com.bancosist.transaction_service.dto.TransactionResponse;
import com.bancosist.transaction_service.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/{accountNumber}/deposit")
    public TransactionResponse deposit(
            @PathVariable String accountNumber,
            @RequestBody @Valid TransactionRequest request
    ) {
        return transactionService.deposit(accountNumber, request);
    }

    @PostMapping("/{accountNumber}/withdraw")
    public TransactionResponse withdraw(
            @PathVariable String accountNumber,
            @RequestBody @Valid TransactionRequest request
    ) {
        return transactionService.withdraw(accountNumber, request);
    }

    @GetMapping("/{accountNumber}/statement")
    public List<TransactionResponse> getStatement(
            @PathVariable String accountNumber,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime startDate,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime endDate
    ) {
        return transactionService.getStatement(accountNumber, startDate, endDate);
    }
}