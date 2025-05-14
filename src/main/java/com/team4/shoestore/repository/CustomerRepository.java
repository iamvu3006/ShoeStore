package com.team4.shoestore.repository;

import com.team4.shoestore.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    List<Customer> findByNameContainingIgnoreCase(String name);
    List<Customer> findByPhoneContaining(String phone);
}
