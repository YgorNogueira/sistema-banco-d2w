package com.bancosist.customer_service.service;

import com.bancosist.customer_service.dto.CreateCustomerRequest;
import com.bancosist.customer_service.dto.CustomerResponse;
import com.bancosist.customer_service.entity.Customer;
import com.bancosist.customer_service.exception.BusinessException;
import com.bancosist.customer_service.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerResponse create(CreateCustomerRequest request) {
        String cleanCpf = request.cpf().replaceAll("\\D", "");

        if (customerRepository.existsByCpf(cleanCpf)) {
            throw new BusinessException("CPF já registrado");
        }

        Customer customer = Customer.builder()
                .name(request.name())
                .cpf(cleanCpf)
                .birthDate(request.birthDate())
                .build();

        Customer savedCustomer = customerRepository.save(customer);

        return toResponse(savedCustomer);
    }

    public CustomerResponse findByCpf(String cpf) {
        String cleanCpf = cpf.replaceAll("\\D", "");

        Customer customer = customerRepository.findByCpf(cleanCpf)
                .orElseThrow(() -> new BusinessException("Cliente não encontrado"));

        return toResponse(customer);
    }

    public void delete(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new BusinessException("Cliente não encontrado");
        }

        customerRepository.deleteById(id);
    }

    private CustomerResponse toResponse(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getCpf(),
                customer.getBirthDate(),
                customer.getCreatedAt()
        );
    }
}