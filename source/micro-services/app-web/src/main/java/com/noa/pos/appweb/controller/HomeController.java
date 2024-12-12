package com.noa.pos.appweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

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
    public String register() {
        return "register";
    }
}
