package com.noa.pos.service;

import com.noa.pos.dto.UserDto;

import java.util.List;

public interface UserService {

    public UserDto saveUser(UserDto user);

    public UserDto getUserByEmail(String email);

    public UserDto getUserByUser(String user);

    public List<UserDto> getUsers(String role);

    public List<UserDto> getAllUsers();

    public UserDto getUserByToken(String token);

    public UserDto updateUser(UserDto user);
}
