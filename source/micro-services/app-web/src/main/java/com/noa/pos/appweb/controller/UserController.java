package com.noa.pos.appweb.controller;

import com.noa.pos.dto.LoginDto;
import com.noa.pos.dto.UserDto;
import com.noa.pos.service.ProfileService;
import com.noa.pos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final ProfileService profileService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(final UserService userService, ProfileService profileService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.profileService = profileService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/signin")
    public String login(@RequestBody LoginDto loginDto) {

        var exist = userService.getUserByUser(loginDto.getUsername());
        if (exist == null) {
            return "login";
        }

        var password = passwordEncoder.encode(loginDto.getPassword());
        if (!passwordEncoder.matches(loginDto.getPassword(), password)) {
            return "login";
        }

        //var roles = profileService.getProfileByUserId(exist.getProfileId());

        return "venta/venta";
    }

    @GetMapping("/listar")
    public ResponseEntity<?> index() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/register")
    public String login(@ModelAttribute UserDto userDto) {

        try {
            userService.saveUser(userDto);
        } catch (Exception e) {
            return "register";
        }

        return "register";
    }


}
