package com.noa.pos.appweb.controller;

import com.noa.pos.dto.DomainDto;
import com.noa.pos.dto.LoginDto;
import com.noa.pos.dto.UserDto;
import com.noa.pos.service.DomainService;
import com.noa.pos.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/domain")
public class DomainController {

    private final DomainService domainService;

    @Autowired
    public DomainController(final DomainService userService, ProfileService profileService, PasswordEncoder passwordEncoder) {
        this.domainService = userService;
    }

    @PostMapping("/list")
    public String list(@ModelAttribute DomainDto domainDto) {

        var exist = domainService.getGropupDom(domainDto.getGroupDom());
        if (exist == null) {
            return "login";
        }

        return "venta/venta";
    }


}
