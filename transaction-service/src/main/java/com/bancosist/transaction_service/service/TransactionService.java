package com.bancosist.transaction_service.service;

import com.bancosist.transaction_service.client.AccountClient;
import com.bancosist.transaction_service.dto.AccountResponse;
import com.bancosist.transaction_service.dto.TransactionRequest;
import com.bancosist.transaction_service.dto.TransactionResponse;
import com.bancosist.transaction_service.entity.Transaction;
import com.bancosist.transaction_service.enums.TransactionType;
import com.bancosist.transaction_service.exception.BusinessException;
import com.bancosist.transaction_service.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountClient accountClient;

    public TransactionResponse deposit(String accountNumber, TransactionRequest request) {
        AccountResponse account = accountClient.findByNumber(accountNumber);

        validateAccount(account);

        BigDecimal newBalance = account.balance().add(request.amount());

        accountClient.updateBalance(accountNumber, newBalance);

        Transaction transaction = Transaction.builder()
                .accountNumber(accountNumber)
                .type(TransactionType.DEPOSIT)
                .amount(request.amount())
                .balanceAfterTransaction(newBalance)
                .build();

        Transaction savedTransaction = transactionRepository.save(transaction);

        return toResponse(savedTransaction);
    }

    public TransactionResponse withdraw(String accountNumber, TransactionRequest request) {
        AccountResponse account = accountClient.findByNumber(accountNumber);

        validateAccount(account);

        if (account.balance().compareTo(request.amount()) < 0) {
            throw new BusinessException("Saldo insuficiente");
        }

        validateDailyWithdrawLimit(accountNumber, request.amount(), account.dailyWithdrawLimit());

        BigDecimal newBalance = account.balance().subtract(request.amount());

        accountClient.updateBalance(accountNumber, newBalance);

        Transaction transaction = Transaction.builder()
                .accountNumber(accountNumber)
                .type(TransactionType.WITHDRAW)
                .amount(request.amount())
                .balanceAfterTransaction(newBalance)
                .build();

        Transaction savedTransaction = transactionRepository.save(transaction);

        return toResponse(savedTransaction);
    }

    private void validateAccount(AccountResponse account) {
        if (!account.active()) {
            throw new BusinessException("Conta está inativa");
        }

        if (account.blocked()) {
            throw new BusinessException("Conta está bloqueada");
        }
    }

    private void validateDailyWithdrawLimit(
            String accountNumber,
            BigDecimal withdrawAmount,
            BigDecimal dailyLimit
    ) {

        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDateTime.now();

        List<Transaction> withdrawals =
                transactionRepository.findByAccountNumberAndTypeAndCreatedAtBetween(
                        accountNumber,
                        TransactionType.WITHDRAW,
                        startOfDay,
                        endOfDay
                );

        BigDecimal totalWithdrawnToday = withdrawals.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal projectedTotal = totalWithdrawnToday.add(withdrawAmount);

        if (projectedTotal.compareTo(dailyLimit) > 0) {
            throw new BusinessException("Limite diário de saque excedido");
        }
    }

    public List<TransactionResponse> getStatement(
            String accountNumber,
            LocalDateTime startDate,
            LocalDateTime endDate
    ) {
        accountClient.findByNumber(accountNumber);

        return transactionRepository
                .findByAccountNumberAndCreatedAtBetweenOrderByCreatedAtDesc(
                        accountNumber,
                        startDate,
                        endDate
                )
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private TransactionResponse toResponse(Transaction transaction) {
        return new TransactionResponse(
                transaction.getId(),
                transaction.getAccountNumber(),
                transaction.getType(),
                transaction.getAmount(),
                transaction.getBalanceAfterTransaction(),
                transaction.getCreatedAt()
        );
    }
}