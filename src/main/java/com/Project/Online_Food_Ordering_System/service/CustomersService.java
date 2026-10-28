package com.Project.Online_Food_Ordering_System.service;

import com.Project.Online_Food_Ordering_System.dto.CustomerRequestDto;
import com.Project.Online_Food_Ordering_System.dto.CustomerResponseDto;
import com.Project.Online_Food_Ordering_System.entity.Customers;
import com.Project.Online_Food_Ordering_System.exception.ResourceNotFoundException;
import com.Project.Online_Food_Ordering_System.repository.CustomersRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomersService {
    private final CustomersRepository customersRepository;

    public CustomersService(CustomersRepository customersRepository) {
        this.customersRepository = customersRepository;
    }

    public List<CustomerResponseDto> findAll() {
        return customersRepository.findAll().stream().map(this::toResponse).toList();
    }

    public CustomerResponseDto findById(Long id) {
        return toResponse(getCustomer(id));
    }

    public CustomerResponseDto create(CustomerRequestDto request) {
        Customers customer = new Customers();
        applyRequest(customer, request);
        return toResponse(customersRepository.save(customer));
    }

    public CustomerResponseDto update(Long id, CustomerRequestDto request) {
        Customers customer = getCustomer(id);
        applyRequest(customer, request);
        return toResponse(customersRepository.save(customer));
    }

    public void delete(Long id) {
        customersRepository.delete(getCustomer(id));
    }

    private Customers getCustomer(Long id) {
        return customersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + id));
    }

    private void applyRequest(Customers customer, CustomerRequestDto request) {
        customer.setName(request.name());
        customer.setEmail(request.email());
        customer.setPhone(request.phone());
        customer.setAddress(request.address());
    }

    private CustomerResponseDto toResponse(Customers customer) {
        return new CustomerResponseDto(
                customer.getCustomerId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getAddress()
        );
    }
}
