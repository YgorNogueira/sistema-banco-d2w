package com.bancosist.customer_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record CreateCustomerRequest(
        @NotBlank(message = "Nome é obrigatório")
        String name,

        @NotBlank(message = "CPF é obrigatório")
        @CPF(message = "CPF é inválido")
        String cpf,

        @NotNull(message = "Data de nascimento é obrigatória")
        LocalDate birthDate
) {
}