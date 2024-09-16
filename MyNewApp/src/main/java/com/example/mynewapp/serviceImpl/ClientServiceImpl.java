package com.example.mynewapp.serviceImpl;

import com.example.mynewapp.entity.Settings;
import com.example.mynewapp.mapper.UserMapper;
import com.example.mynewapp.repositories.ClientRepository;
import com.example.mynewapp.dto.ClientDto;
import com.example.mynewapp.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepository clientRepository;
    private final UserMapper userMapper;

    @Override
    public void register(ClientDto clientDto) {
        if(clientRepository.findByLogin(clientDto.getLogin()) != null)
            throw new RuntimeException("Client with id: " + clientDto.getLogin() + " already registered");
        String password = BCrypt.hashpw(clientDto.getPassword(), BCrypt.gensalt());
        ClientDto newClientDto = new ClientDto();
        newClientDto.setId(clientDto.getId());
        newClientDto.setLogin(clientDto.getLogin());
        newClientDto.setPassword(password);
        clientRepository.save(userMapper.clientToSettings(newClientDto));
    }

    @Override
    public void checkCredentials(String login, String password) {
        Settings optionalUserEntity = clientRepository
                .findByLogin(login);
        if (optionalUserEntity == null)
            throw new RuntimeException( "Client with login: " + login + " not found");

        Settings settingsEntity = optionalUserEntity;

        if (!BCrypt.checkpw(password, settingsEntity.getPassword()))
            throw new RuntimeException("Password is incorrect");
    }
}
