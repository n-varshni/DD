package com.example.dd1.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dd1.entity.customer;

public interface customerrepo extends JpaRepository<customer, Long> {
    Optional<customer> findByEmailAndPassword(String email,String password);
    Optional<customer> findByEmail(String email);
}
