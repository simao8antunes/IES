package com.example.demo.Service;

import java.util.List;

import com.example.demo.Entity.User;

public interface UserService {
    User createUser(User user);

    User getUserById(Long userId);

    User getUserByEmail(String email);

    List<User> getAllUsers();

    User updateUser(User user);

    void deleteUser(Long userId);
}