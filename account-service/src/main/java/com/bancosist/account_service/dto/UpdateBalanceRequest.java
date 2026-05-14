package com.bancosist.account_service.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record UpdateBalanceRequest(
        @NotNull(message = "Saldo é obrigatório")
        BigDecimal balance
) {
}