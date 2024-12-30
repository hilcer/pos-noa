package com.noa.pos.service.imp;

import com.noa.pos.dto.UserDto;
import com.noa.pos.model.entity.UserEntity;
import com.noa.pos.model.repository.UserRepository;
import com.noa.pos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    UserServiceImp(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto saveUser(UserDto userDto) {
        var user = dtoToUser(userDto);
        user.setLastUser("ADM");
        user.setLastTime(LocalDateTime.now());
        user.setEnabled(true);
        user.setProfileId(1);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userToDto(userRepository.save(user));
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return null;
    }

    @Override
    public UserDto getUserByUser(String user) {
        var entity = userRepository.findByUsername(user);

        if (entity == null) return null;

        return userToDto(entity);
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

    private UserEntity dtoToUser(UserDto dto) {
        UserEntity user = new UserEntity();
        user.setUsername(dto.getUsername());
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
        user.setCompanyId(dto.getCompanyId());
        user.setSucursalId(dto.getSucursalId());
        return user;
    }

    private UserDto userToDto(UserEntity user) {
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setUsername(user.getUsername());
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
        userDto.setCompanyId(user.getCompanyId());
        userDto.setSucursalId(user.getSucursalId());
        return userDto;
    }

}
