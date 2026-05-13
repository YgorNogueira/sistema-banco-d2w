package com.bancosist.customer_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record CreateCustomerRequest(
        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "CPF is required")
        @CPF(message = "CPF is invalid")
        String cpf,

        @NotNull(message = "Birth date is required")
        LocalDate birthDate
) {
}