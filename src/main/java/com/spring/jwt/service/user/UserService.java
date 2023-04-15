package com.spring.jwt.service.user;

import com.spring.jwt.dto.UserDTO;
import com.spring.jwt.entity.User;
import com.spring.jwt.util.BaseResponseDTO;

import java.util.Optional;

public interface UserService {

   BaseResponseDTO registerAccount(UserDTO userDTO);

}
