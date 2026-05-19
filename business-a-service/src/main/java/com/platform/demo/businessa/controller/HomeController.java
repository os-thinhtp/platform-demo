package com.platform.demo.businessa.controller;

import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/", "/home"})
    public String home(Authentication authentication, Model model) {
        model.addAttribute("serviceName", "Business A Portal");
        model.addAttribute("username", authentication.getName());
        model.addAttribute("roles", authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .sorted()
            .collect(Collectors.toList()));
        return "index";
    }
}
