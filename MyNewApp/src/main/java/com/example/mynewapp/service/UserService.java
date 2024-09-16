package com.example.mynewapp.service;

import com.example.mynewapp.entity.User;

import java.util.Optional;

public interface UserService {
    void saveUser(User user);
    Optional<User> findById(Long UserId);
}
