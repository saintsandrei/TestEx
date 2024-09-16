package com.example.mynewapp.serviceImpl;

import com.example.mynewapp.entity.User;
import com.example.mynewapp.mapper.UserMapper;
import com.example.mynewapp.repositories.UserRepository;
import com.example.mynewapp.dto.UserDto;
import com.example.mynewapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public void saveUser(User user) {
        if(userRepository.findByUsername(user.getUsername()) != null)
            throw new RuntimeException("Client with id: " + user.getUsername() + " already registered");
//        String password = BCrypt.hashpw(client.getPassword(), BCrypt.gensalt());
        UserDto newUser = new UserDto();
        newUser.setId(user.getId());
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        newUser.setEmail(user.getEmail());
        newUser.setPhoneNumber(user.getPhoneNumber());
        userRepository.save(userMapper.userDtoToUser(newUser));
    }

    @Override
    public Optional<User> findById(Long UserId) {
        return userRepository.findById(UserId);
    }
}
