package ru.makridina.kursovaya.service;


import ru.makridina.kursovaya.dto.UserDto;
import ru.makridina.kursovaya.entity.User;

import java.util.List;

public interface UserService {
    void saveUserRole(UserDto userDTO);
    void saveUser(UserDto userDto);
    User findUserByEmail(String email);
    List<UserDto> findAllUsers();

    UserDto mapToUserDto(User user);
}