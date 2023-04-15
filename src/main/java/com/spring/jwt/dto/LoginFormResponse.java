package com.spring.jwt.dto;


import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Data
public class LoginFormResponse {
    private String username;
    private String accessToken;
    private List<String> roles;
}
