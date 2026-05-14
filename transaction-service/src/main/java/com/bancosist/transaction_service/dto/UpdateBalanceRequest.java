package com.bancosist.transaction_service.dto;

import java.math.BigDecimal;

public record UpdateBalanceRequest(
        BigDecimal balance
) {
}