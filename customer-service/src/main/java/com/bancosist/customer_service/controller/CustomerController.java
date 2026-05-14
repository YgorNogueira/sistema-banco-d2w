package com.bancosist.customer_service.controller;

import com.bancosist.customer_service.dto.CreateCustomerRequest;
import com.bancosist.customer_service.dto.CustomerResponse;
import com.bancosist.customer_service.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse create(@RequestBody @Valid CreateCustomerRequest request) {
        return customerService.create(request);
    }

    @GetMapping("/cpf/{cpf}")
    public CustomerResponse findByCpf(@PathVariable String cpf) {
        return customerService.findByCpf(cpf);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        customerService.delete(id);
    }
}