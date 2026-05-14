package com.bancosist.account_service.client;

import com.bancosist.account_service.dto.CustomerResponse;
import com.bancosist.account_service.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class CustomerClient {

    private final RestClient.Builder restClientBuilder;

    @Value("${services.customer.url}")
    private String customerServiceUrl;

    public CustomerResponse findByCpf(String cpf) {
        try {
            return restClientBuilder.build()
                    .get()
                    .uri(customerServiceUrl + "/customers/cpf/" + cpf)
                    .retrieve()
                    .body(CustomerResponse.class);
        } catch (Exception exception) {
            throw new BusinessException("Cliente não encontrado para CPF: " + cpf);
        }
    }
}