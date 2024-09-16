package com.example.mynewapp.controllers;

import com.example.mynewapp.entity.User;
import com.example.mynewapp.exception.LoginException;
import com.example.mynewapp.exception.RegistrationException;
import com.example.mynewapp.dto.ClientDto;
import com.example.mynewapp.response.ErrorResponse;
import com.example.mynewapp.response.TokenResponse;
import com.example.mynewapp.service.ClientService;
import com.example.mynewapp.service.TokenService;
import com.example.mynewapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<String> register(@RequestBody User user){
        ClientDto clientDto = new ClientDto();
        clientDto.setLogin(user.getUsername());
        clientDto.setPassword(user.getPassword());
        clientService.register(clientDto);
        userService.saveUser(user);
        return ResponseEntity.ok("Registered");
    }
    @PostMapping("/token")
    public TokenResponse getToken(@RequestBody ClientDto clientDto){
        clientService.checkCredentials(clientDto.getLogin(), clientDto.getPassword());
        return new TokenResponse(tokenService.generateToken(clientDto.getLogin()));

    }

    @ExceptionHandler({RegistrationException.class, LoginException.class})
    public ResponseEntity<ErrorResponse> handleUserRegistrationException(RuntimeException ex){
        return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage()));
    }
}
