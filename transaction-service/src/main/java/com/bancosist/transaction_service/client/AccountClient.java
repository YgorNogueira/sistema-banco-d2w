package com.bancosist.transaction_service.client;

import com.bancosist.transaction_service.dto.AccountResponse;
import com.bancosist.transaction_service.dto.UpdateBalanceRequest;
import com.bancosist.transaction_service.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class AccountClient {

    private final RestClient.Builder restClientBuilder;

    @Value("${services.account.url}")
    private String accountServiceUrl;

    public AccountResponse findByNumber(String accountNumber) {
        try {
            return restClientBuilder.build()
                    .get()
                    .uri(accountServiceUrl + "/accounts/" + accountNumber)
                    .retrieve()
                    .body(AccountResponse.class);
        } catch (Exception exception) {
            throw new BusinessException("Conta não encontrada para número: " + accountNumber);
        }
    }

    public AccountResponse updateBalance(String accountNumber, BigDecimal balance) {
        try {
            return restClientBuilder.build()
                    .patch()
                    .uri(accountServiceUrl + "/accounts/" + accountNumber + "/balance")
                    .body(new UpdateBalanceRequest(balance))
                    .retrieve()
                    .body(AccountResponse.class);
        } catch (Exception exception) {
            throw new BusinessException("Não foi possível atualizar o saldo da conta: " + accountNumber);
        }
    }
}