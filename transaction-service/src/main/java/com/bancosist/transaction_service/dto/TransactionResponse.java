package com.bancosist.transaction_service.dto;

import com.bancosist.transaction_service.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponse(
        Long id,
        String accountNumber,
        TransactionType type,
        BigDecimal amount,
        BigDecimal balanceAfterTransaction,
        LocalDateTime createdAt
) {
}