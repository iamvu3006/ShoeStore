package com.team4.shoestore.repository;

import com.team4.shoestore.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByCustomer_CustomerId(int customerId);
    List<Order> findByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<Order> findByPaymentStatus(boolean paymentStatus);
    
    @Query("SELECT o FROM Order o JOIN o.customer c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :customerName, '%'))")
    List<Order> findByCustomerNameContaining(String customerName);
}
