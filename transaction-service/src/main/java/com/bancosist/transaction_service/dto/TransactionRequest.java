package com.bancosist.transaction_service.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransactionRequest(
        @NotNull(message = "Montante é obrigatório")
        @DecimalMin(value = "0.01", message = "A quantia deve ser maior que zero")
        BigDecimal amount
) {
}