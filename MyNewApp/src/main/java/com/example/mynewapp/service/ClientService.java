package com.example.mynewapp.service;

import com.example.mynewapp.dto.ClientDto;

public interface ClientService {
    void register(ClientDto client);
    void checkCredentials(String clientId, String clientSecret);
}
