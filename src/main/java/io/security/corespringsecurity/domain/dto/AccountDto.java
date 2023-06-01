package io.security.corespringsecurity.domain.dto;

import java.util.List;

import lombok.Builder;

@Builder
public record AccountDto(String id,
                         String username,
                         String email,
                         int age,
                         String password,
                         List<String> roles) {

}