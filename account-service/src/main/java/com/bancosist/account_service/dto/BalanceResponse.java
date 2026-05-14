package com.bancosist.account_service.dto;

import java.math.BigDecimal;

public record BalanceResponse(
        String number,
        String agency,
        BigDecimal balance
) {
}