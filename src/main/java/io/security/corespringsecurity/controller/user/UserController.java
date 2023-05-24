package io.security.corespringsecurity.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {

    @GetMapping(value="/users")
    public String createUser() throws Exception {

        return "user/login/register";
    }

    // @PostMapping(value="/users")
    // public String createUser(UserDto userDto) throws Exception {
    //
    //     ModelMapper modelMapper = new ModelMapper();
    //     Account account = modelMapper.map(userDto, Account.class);
    //     userService.createUser(account);
    //
    //     return "redirect:/";
    // }
}