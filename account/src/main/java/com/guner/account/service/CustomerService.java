package com.guner.account.service;

import com.guner.account.exception.CustomerNotFoundException;
import com.guner.account.model.Customer;
import com.guner.account.repository.CustomerRepository;
import org.springframework.stereotype.Service;

//SOLID = Sıngle Responsibility
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    protected Customer findCustomerById(String id){
        return customerRepository.findById(id).orElseThrow(()->new CustomerNotFoundException("Customer could not found" +
                "by id: " + id));
    }
}
