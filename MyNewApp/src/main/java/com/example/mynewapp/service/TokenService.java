package com.example.mynewapp.service;

public interface TokenService {
    String generateToken(String clientId);
    boolean checkToken(String token);
}
