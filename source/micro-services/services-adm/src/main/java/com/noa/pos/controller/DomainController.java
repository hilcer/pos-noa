package com.noa.pos.controller;

import com.noa.pos.dto.UserDto;
import com.noa.pos.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DomainController {

    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());

    private final UserService userService;

    @GetMapping(path = "/list")
    public ResponseEntity<?> list() {
        LOGGER.log(Level.INFO, "Listar usuario");
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping(path = "/save")
    public ResponseEntity<?> save(@RequestBody UserDto userDto) {
        LOGGER.log(Level.INFO, "guardar usuario");
        return new ResponseEntity<>(userService.saveUser(userDto), HttpStatus.OK);
    }
}
