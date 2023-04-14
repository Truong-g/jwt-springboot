package com.spring.jwt.service.user;

import com.spring.jwt.dto.UserDTO;
import com.spring.jwt.util.BaseResponseDTO;

public interface UserService {

   BaseResponseDTO registerAccount(UserDTO userDTO);
}
