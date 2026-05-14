package com.bancosist.account_service.controller;

import com.bancosist.account_service.dto.AccountResponse;
import com.bancosist.account_service.dto.BalanceResponse;
import com.bancosist.account_service.dto.CreateAccountRequest;
import com.bancosist.account_service.service.AccountService;
import com.bancosist.account_service.dto.UpdateBalanceRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse create(@RequestBody @Valid CreateAccountRequest request) {
        return accountService.create(request);
    }

    @GetMapping("/{number}")
    public AccountResponse findByNumber(@PathVariable String number) {
        return accountService.findByNumber(number);
    }

    @GetMapping("/{number}/balance")
    public BalanceResponse getBalance(@PathVariable String number) {
        return accountService.getBalance(number);
    }

    @PatchMapping("/{number}/block")
    public AccountResponse block(@PathVariable String number) {
        return accountService.block(number);
    }

    @PatchMapping("/{number}/unblock")
    public AccountResponse unblock(@PathVariable String number) {
        return accountService.unblock(number);
    }

    @PatchMapping("/{number}/balance")
    public AccountResponse updateBalance(@PathVariable String number, @RequestBody @Valid UpdateBalanceRequest request) {
        return accountService.updateBalance(number, request.balance());
    }
}