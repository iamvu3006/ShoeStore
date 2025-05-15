package com.team4.shoestore.service;

import com.team4.shoestore.model.User;
import com.team4.shoestore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

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
    
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    public List<User> findUsersByUsernameContainingIgnoreCase(String s) {
        String search = s.toLowerCase();
        return userRepository.findAll()
            .stream()
            .filter(user -> user.getUsername().toLowerCase().contains(search))
            .collect(Collectors.toList());
    }

    public void AddNewUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        userRepository.save(user);
    }

    public void DeleteUser(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }

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
