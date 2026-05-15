package com.bancosist.account_service.service;

import com.bancosist.account_service.client.CustomerClient;
import com.bancosist.account_service.dto.AccountResponse;
import com.bancosist.account_service.dto.CreateAccountRequest;
import com.bancosist.account_service.dto.CustomerResponse;
import com.bancosist.account_service.entity.Account;
import com.bancosist.account_service.exception.BusinessException;
import com.bancosist.account_service.repository.AccountRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CustomerClient customerClient;

    @InjectMocks
    private AccountService accountService;

    @Test
    void shouldCreateAccountSuccessfully() {
        String cpf = "52998224725";

        CreateAccountRequest request = new CreateAccountRequest(cpf);

        CustomerResponse customer = new CustomerResponse(
                1L,
                "Gabriel Pontes",
                cpf,
                LocalDate.of(1998, 5, 10),
                LocalDateTime.now()
        );

        Account savedAccount = Account.builder()
                .id(1L)
                .number("123456")
                .agency("0001")
                .customerCpf(cpf)
                .balance(BigDecimal.ZERO)
                .blocked(false)
                .active(true)
                .dailyWithdrawLimit(BigDecimal.valueOf(2000))
                .createdAt(LocalDateTime.now())
                .build();

        when(customerClient.findByCpf(cpf)).thenReturn(customer);
        when(accountRepository.existsByCustomerCpf(cpf)).thenReturn(false);
        when(accountRepository.existsByNumber(anyString())).thenReturn(false);
        when(accountRepository.save(any(Account.class))).thenReturn(savedAccount);

        AccountResponse response = accountService.create(request);

        assertThat(response.customerCpf()).isEqualTo(cpf);
        assertThat(response.agency()).isEqualTo("0001");
        assertThat(response.balance()).isEqualTo(BigDecimal.ZERO);
        assertThat(response.blocked()).isFalse();

        verify(customerClient).findByCpf(cpf);
        verify(accountRepository).save(any(Account.class));
    }

    @Test
    void shouldNotCreateAccountWhenCustomerAlreadyHasAccount() {
        String cpf = "52998224725";

        CreateAccountRequest request = new CreateAccountRequest(cpf);

        CustomerResponse customer = new CustomerResponse(
                1L,
                "Gabriel Pontes",
                cpf,
                LocalDate.of(1998, 5, 10),
                LocalDateTime.now()
        );

        when(customerClient.findByCpf(cpf)).thenReturn(customer);
        when(accountRepository.existsByCustomerCpf(cpf)).thenReturn(true);

        assertThatThrownBy(() -> accountService.create(request))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Cliente já possui uma conta cadastrada");

        verify(accountRepository, never()).save(any(Account.class));
    }
}