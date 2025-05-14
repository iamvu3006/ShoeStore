package com.team4.shoestore.service;

import com.team4.shoestore.model.Order;
import com.team4.shoestore.model.OrderItem;
import com.team4.shoestore.repository.OrderItemRepository;
import com.team4.shoestore.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private OrderItemRepository orderItemRepository;
    
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    
    public Order getOrderById(Integer id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn có ID: " + id));
    }
    
    @Transactional
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }
    
    @Transactional
    public Order createOrderWithItems(Order order, List<OrderItem> items) {
        Order savedOrder = orderRepository.save(order);
        
        for (OrderItem item : items) {
            item.setOrder(savedOrder);
            orderItemRepository.save(item);
        }
        
        return savedOrder;
    }
    
    public void deleteOrder(Integer id) {
        orderRepository.deleteById(id);
    }
    
    public List<Order> findOrdersByCustomerId(Integer customerId) {
        return orderRepository.findByCustomer_CustomerId(customerId);
    }
    
    public List<Order> findOrdersByCustomerName(String customerName) {
        return orderRepository.findByCustomerNameContaining(customerName);
    }
    
    public List<Order> findOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findByOrderDateBetween(startDate, endDate);
    }
    
    public List<Order> findOrdersByPaymentStatus(boolean paymentStatus) {
        return orderRepository.findByPaymentStatus(paymentStatus);
    }
    
    @Transactional
    public Order updatePaymentStatus(Integer orderId, boolean status) {
        Order order = getOrderById(orderId);
        order.setPaymentStatus(status);
        return orderRepository.save(order);
    }
}
