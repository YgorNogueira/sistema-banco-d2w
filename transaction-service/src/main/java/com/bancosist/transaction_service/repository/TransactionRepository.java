package com.bancosist.transaction_service.repository;

import com.bancosist.transaction_service.entity.Transaction;
import com.bancosist.transaction_service.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByAccountNumberAndCreatedAtBetweenOrderByCreatedAtDesc(
            String accountNumber,
            LocalDateTime startDate,
            LocalDateTime endDate
    );

    List<Transaction> findByAccountNumberAndTypeAndCreatedAtBetween(
            String accountNumber,
            TransactionType type,
            LocalDateTime startDate,
            LocalDateTime endDate
    );
}