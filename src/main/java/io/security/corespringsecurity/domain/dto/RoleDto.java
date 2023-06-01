package io.security.corespringsecurity.domain.dto;

import lombok.Builder;

@Builder
public record RoleDto(String id,
                       String roleName,
                       String roleDesc){

}
