package com.bancosist.account_service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountResponse(
        Long id,
        String number,
        String agency,
        String customerCpf,
        BigDecimal balance,
        Boolean blocked,
        Boolean active,
        BigDecimal dailyWithdrawLimit,
        LocalDateTime createdAt
) {
}