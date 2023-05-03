package com.guner.account.service;

import com.guner.account.dto.CustomerDto;
import com.guner.account.dto.converter.CustomerDtoConverter;
import com.guner.account.exception.CustomerNotFoundException;
import com.guner.account.model.Customer;
import com.guner.account.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

//SOLID = Sıngle Responsibility
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerDtoConverter converter;

    public CustomerService(CustomerRepository customerRepository, CustomerDtoConverter converter) {
        this.customerRepository = customerRepository;
        this.converter = converter;
    }

    protected Customer findCustomerById(String id){
        return customerRepository.findById(id).orElseThrow(()->new CustomerNotFoundException("Customer could not found" +
                "by id: " + id));
    }

    public CustomerDto getCustomerById(String customerId){
        return converter.convertToCustomerDto(findCustomerById(customerId));
    }

    public List<CustomerDto> getAllCustomer(){
        return customerRepository.findAll()
                .stream()
                .map(converter::convertToCustomerDto)
                .collect(Collectors.toList());
    }
}
