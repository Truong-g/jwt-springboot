package com.spring.jwt.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginFormRequest {

    @NotNull
    private String username;
    @NotNull
    private String password;
}
