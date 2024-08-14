package com.example.dd1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dd1.entity.AddToCart;

public interface AddToCartRepo extends JpaRepository<AddToCart, Long>{

    List<AddToCart> findByCustomerId(Long customerId);
}
