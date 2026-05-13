package com.bancosist.customer_service.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record CustomerResponse(
        Long id,
        String name,
        String cpf,
        LocalDate birthDate,
        LocalDateTime createdAt
) {
}