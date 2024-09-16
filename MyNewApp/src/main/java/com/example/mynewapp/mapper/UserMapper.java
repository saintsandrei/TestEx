package com.example.mynewapp.mapper;

import com.example.mynewapp.entity.Settings;
import com.example.mynewapp.entity.User;
import com.example.mynewapp.dto.ClientDto;
import com.example.mynewapp.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    Settings clientToSettings(ClientDto clientDto);
    User userDtoToUser(UserDto userDto);
}
