package com.team4.shoestore.service;

import com.team4.shoestore.model.User;
import com.team4.shoestore.model.Customer;
import com.team4.shoestore.repository.UserRepository;
import com.team4.shoestore.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public User getUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }
    
    public boolean authenticate(String username, String password) {
        // Triển khai xác thực (nên băm mật khẩu)
        return userRepository.findByUsername(username)
                .map(user -> user.getPassword().equals(password))
                .orElse(false);
    }
    
    public User save(User user) {
        return userRepository.save(user);
    }
    
    @Transactional
    public void deleteUser(Integer id) {
        User user = getUserById(id);
        
        // Check if user is an admin
        if (user.getRole() == User.Role.ADMIN) {
            // Count number of admins
            long adminCount = userRepository.findAll().stream()
                .filter(u -> u.getRole() == User.Role.ADMIN)
                .count();
            
            if (adminCount <= 1) {
                throw new RuntimeException("Không thể xóa admin cuối cùng trong hệ thống!");
            }
        }
        
        // Remove customer reference if exists
        Customer customer = user.getCustomer();
        if (customer != null) {
            customer.setUser(null);
            customerRepository.save(customer);
        }
        
        userRepository.deleteById(id);
    }

    public List<User> findUsersByUsernameContainingIgnoreCase(String s) {
        String search = s.toLowerCase();
        return userRepository.findAll()
            .stream()
            .filter(user -> user.getUsername().toLowerCase().contains(search))
            .collect(Collectors.toList());
    }

    @Transactional
    public void AddNewUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        userRepository.save(user);
    }

    @Transactional
    public void DeleteUser(Integer id) {
        deleteUser(id);
    }

    @Transactional
    public void UpdateUser(User user) {
        if (user == null || user.getUserId() <= 0) {
            throw new IllegalArgumentException("User cannot be null and User ID must be greater than 0");
        }
        if (!userRepository.existsById(user.getUserId())) {
            throw new RuntimeException("User not found with ID: " + user.getUserId());
        }
        userRepository.save(user);
    }

}
