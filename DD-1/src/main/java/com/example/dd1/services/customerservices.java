package com.example.dd1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dd1.entity.customer;
import com.example.dd1.repository.customerrepo;

import java.util.List;
import java.util.Optional;

@Service
public class customerservices {

    @Autowired
    private customerrepo customerRepository;

    public customer signUp(customer customer) {
        return customerRepository.save(customer);
    }

    public Optional<customer> login(String email, String password) {
        return customerRepository.findByEmailAndPassword(email, password);
    }

    public Optional<customer> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public Optional<customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    public customer saveCustomer(customer customer) {
        return customerRepository.save(customer);
    }
    
    public List<customer> findAll() {
        return customerRepository.findAll();
    }
    
    public long countCustomers() {
        return customerRepository.count();
    }


}
