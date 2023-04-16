package com.spring.jwt.service.user;

import com.spring.jwt.dto.RegisterFormRequest;
import com.spring.jwt.entity.User;
import com.spring.jwt.util.BaseResponseDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {

   BaseResponseDTO registerAccount(RegisterFormRequest registerFormRequest);

   List<User> getAllUsers();

   Optional<User> getUser(Long userId);
   Optional<User> getUserByUsername(String username);

   public void deleteUser(Long userId);

}
