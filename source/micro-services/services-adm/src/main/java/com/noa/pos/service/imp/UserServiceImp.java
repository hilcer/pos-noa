package com.noa.pos.service.imp;

import com.noa.pos.dto.UserDto;
import com.noa.pos.model.entity.User;
import com.noa.pos.model.repository.UserRepository;
import com.noa.pos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    @Autowired
    UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto saveUser(UserDto userDto) {
        var user = dtoToUser(userDto);
        user.setLastUser("ADM");
        user.setLastTime(new Date());
        return userToDto(userRepository.save(user));
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return null;
    }

    @Override
    public List<UserDto> getUsers(String role) {
        return List.of();
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().parallelStream().map(this::userToDto).toList();
    }

    @Override
    public UserDto getUserByToken(String token) {
        return null;
    }

    @Override
    public UserDto updateUser(UserDto user) {
        return null;
    }

    private User dtoToUser(UserDto dto) {
        User user = new User();
        user.setUser(dto.getUser());
        user.setPassword(dto.getPassword());
        user.setMobileNumber(dto.getMobileNumber());
        user.setFullName(dto.getFullName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setAddress(dto.getAddress());
        user.setCity(dto.getCity());
        user.setState(dto.getState());
        user.setPinCode(dto.getPinCode());
        user.setProfileId(dto.getProfileId());
        user.setEnabled(dto.getEnabled());
        user.setFailedAttempt(dto.getFailedAttempt());
        user.setLockTime(dto.getLockTime());
        user.setResetToken(dto.getResetToken());
        user.setLastUser(dto.getLastUser());
        user.setLastTime(dto.getLastTime());
        return user;
    }

    private UserDto userToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setUser(user.getUser());
        userDto.setPassword(user.getPassword());
        userDto.setMobileNumber(user.getMobileNumber());
        userDto.setFullName(user.getFullName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setAddress(user.getAddress());
        userDto.setCity(user.getCity());
        userDto.setState(user.getState());
        userDto.setPinCode(user.getPinCode());
        userDto.setProfileId(user.getProfileId());
        userDto.setEnabled(user.getEnabled());
        userDto.setFailedAttempt(user.getFailedAttempt());
        userDto.setLockTime(user.getLockTime());
        userDto.setResetToken(user.getResetToken());
        userDto.setLastUser(user.getLastUser());
        userDto.setLastTime(user.getLastTime());
        return userDto;
    }

}
