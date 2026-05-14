package com.bancosist.account_service.repository;

import com.bancosist.account_service.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsByCustomerCpf(String customerCpf);

    boolean existsByNumber(String number);

    Optional<Account> findByNumber(String number);
}