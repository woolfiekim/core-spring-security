package io.security.corespringsecurity.domain.dto;

import java.util.Set;

import io.security.corespringsecurity.domain.entity.Role;
import lombok.Builder;

@Builder
public record ResourcesDto(String id,
                           String resourceName,
                           String httpMethod,
                           int orderNum,
                           String resourceType,
                           String roleName,
                           Set<Role> roleSet){

}