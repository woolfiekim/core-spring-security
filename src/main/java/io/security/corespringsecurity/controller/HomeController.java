package io.security.corespringsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class HomeController {

    @GetMapping(value="/")
    public String home() throws Exception {
        return "home";
    }

    @GetMapping(value="/login")
    public String login() throws Exception {
        return "login";
    }

}