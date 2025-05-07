package com.noa.pos.api.controller;

import com.noa.pos.api.dto.UserDto;
import com.noa.pos.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/user/v1")
public class UserController {

    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());

    private final UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

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
