package io.security.corespringsecurity.controller.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(){
        return "user/login/login";
    }

    @GetMapping(value="/denied")
    public String accessDenied() throws Exception {

        return "user/login/denied";
    }
}
