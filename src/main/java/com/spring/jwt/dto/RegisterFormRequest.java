package com.spring.jwt.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterFormRequest {
    @NotNull
    private String username;
    @NotNull
    private String fullname;
    @NotNull
    private String password;
    @NotNull
    private String role;
}
