package com.spring.jwt.controller;


import com.spring.jwt.dto.CustomResponse;
import com.spring.jwt.entity.User;
import com.spring.jwt.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @GetMapping
    public ResponseEntity<CustomResponse<List<User>>> getAllUsers() {
        return ResponseEntity.ok(new CustomResponse<>(
                HttpStatus.OK.value(),
                "success",
                userService.getAllUsers()
                )
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<Object>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(new CustomResponse<>(
                        HttpStatus.OK.value(),
                        "success",
                        null
                )
        );
    }
}
