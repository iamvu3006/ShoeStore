package com.team4.shoestore.service;

import com.team4.shoestore.model.Customer;
import com.team4.shoestore.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer getCustomerById(Integer id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + id));
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer updateCustomer(Integer id, Customer customer) {
        Customer existing = getCustomerById(id);
        existing.setName(customer.getName());
        existing.setPhone(customer.getPhone());
        existing.setJoinDate(customer.getJoinDate());
        existing.setUser(customer.getUser());
        return customerRepository.save(existing);
    }

    public void deleteCustomer(Integer id) {
        Customer existing = getCustomerById(id);
        customerRepository.delete(existing);
    }
}
