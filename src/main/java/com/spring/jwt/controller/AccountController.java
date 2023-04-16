package com.spring.jwt.controller;


import com.spring.jwt.dto.RegisterFormRequest;
import com.spring.jwt.service.user.UserService;
import com.spring.jwt.util.BaseResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<BaseResponseDTO> register(@Valid @RequestBody RegisterFormRequest registerFormRequest) {
        return ResponseEntity.ok(userService.registerAccount(registerFormRequest));
    }
}
