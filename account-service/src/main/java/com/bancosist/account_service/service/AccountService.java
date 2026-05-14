package com.bancosist.account_service.service;

import com.bancosist.account_service.client.CustomerClient;
import com.bancosist.account_service.dto.AccountResponse;
import com.bancosist.account_service.dto.BalanceResponse;
import com.bancosist.account_service.dto.CreateAccountRequest;
import com.bancosist.account_service.entity.Account;
import com.bancosist.account_service.exception.BusinessException;
import com.bancosist.account_service.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountService {

    private static final String DEFAULT_AGENCY = "0001";

    private final AccountRepository accountRepository;
    private final CustomerClient customerClient;

    public AccountResponse create(CreateAccountRequest request) {
        String cleanCpf = request.cpf().replaceAll("\\D", "");

        customerClient.findByCpf(cleanCpf);

        if (accountRepository.existsByCustomerCpf(cleanCpf)) {
            throw new BusinessException("Cliente já possui uma conta cadastrada");
        }

        Account account = Account.builder()
                .customerCpf(cleanCpf)
                .agency(DEFAULT_AGENCY)
                .number(generateAccountNumber())
                .balance(BigDecimal.ZERO)
                .blocked(false)
                .active(true)
                .dailyWithdrawLimit(BigDecimal.valueOf(2000))
                .build();

        Account savedAccount = accountRepository.save(account);

        return toResponse(savedAccount);
    }

    public AccountResponse findByNumber(String number) {
        Account account = getAccountByNumber(number);
        return toResponse(account);
    }

    public BalanceResponse getBalance(String number) {
        Account account = getAccountByNumber(number);

        return new BalanceResponse(
                account.getNumber(),
                account.getAgency(),
                account.getBalance()
        );
    }

    public AccountResponse updateBalance(String number, BigDecimal newBalance) {
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException("Saldo da conta não pode ser negativo");
        }

        Account account = getAccountByNumber(number);
        account.setBalance(newBalance);

        return toResponse(accountRepository.save(account));
    }

    public AccountResponse block(String number) {
        Account account = getAccountByNumber(number);
        account.setBlocked(true);

        return toResponse(accountRepository.save(account));
    }

    public AccountResponse unblock(String number) {
        Account account = getAccountByNumber(number);
        account.setBlocked(false);

        return toResponse(accountRepository.save(account));
    }

    private Account getAccountByNumber(String number) {
        return accountRepository.findByNumber(number)
                .orElseThrow(() -> new BusinessException("Conta não encontrada"));
    }

    private String generateAccountNumber() {
        String number;

        do {
            number = String.valueOf(100000 + new Random().nextInt(900000));
        } while (accountRepository.existsByNumber(number));

        return number;
    }

    private AccountResponse toResponse(Account account) {
        return new AccountResponse(
                account.getId(),
                account.getNumber(),
                account.getAgency(),
                account.getCustomerCpf(),
                account.getBalance(),
                account.getBlocked(),
                account.getActive(),
                account.getDailyWithdrawLimit(),
                account.getCreatedAt()
        );
    }
}