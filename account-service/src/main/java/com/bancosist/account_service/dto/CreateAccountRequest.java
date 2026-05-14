package com.bancosist.account_service.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record CreateAccountRequest(
        @NotBlank(message = "CPF é obrigatório")
        @CPF(message = "CPF é inválido")
        String cpf
) {
}