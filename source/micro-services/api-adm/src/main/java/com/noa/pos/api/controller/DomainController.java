package com.noa.pos.api.controller;

import com.noa.pos.api.dto.DomainDto;
import com.noa.pos.api.service.DomainService;
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
@RequestMapping("/domain/v1")
public class DomainController {

    private static final Logger LOGGER = Logger.getLogger(DomainController.class.getName());

    private final DomainService domainService;

    @Autowired
    public DomainController(DomainService domainService) {
        this.domainService = domainService;
    }

    @GetMapping(path = "/list")
    public ResponseEntity<?> list() {
        LOGGER.log(Level.INFO, "Listar usuario");
        return new ResponseEntity<>(domainService.getAllDomains(), HttpStatus.OK);
    }

    @GetMapping(path = "/save")
    public ResponseEntity<?> save(@RequestBody DomainDto userDto) {
        LOGGER.log(Level.INFO, "guardar usuario");
        return new ResponseEntity<>(domainService.saveDomain(userDto), HttpStatus.OK);
    }
}
