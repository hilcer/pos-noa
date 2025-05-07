package com.noa.pos.appweb.controller;

import com.noa.pos.service.CompanyService;
import com.noa.pos.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    private final CompanyService companyService;
    private final ProfileService profileService;

    @Autowired
    public HomeController(final CompanyService companyService, ProfileService profileService) {
        this.companyService = companyService;
        this.profileService = profileService;
    }

    @GetMapping("/")
    public String index() {
        return "login";
    }

    @GetMapping("/home/login")
    public String login() {
        return "login";
    }

    @GetMapping("/home/logout")
    public String logout() {
        return "login";
    }

    @GetMapping("/home/register")
    public String register(Model model) {
        model.addAttribute("profiles", profileService.getAllEnabledProfiles());
        model.addAttribute("companies", companyService.getAllCompanies());
        return "register";
    }
}
