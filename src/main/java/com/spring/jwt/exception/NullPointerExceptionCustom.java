package com.spring.jwt.exception;


import com.spring.jwt.dto.CustomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class NullPointerExceptionCustom {

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> handleNullPointer(NullPointerException ex) {

        return ResponseEntity.badRequest().body(new CustomResponse<>(HttpStatus.BAD_REQUEST.value(), ex.getLocalizedMessage(), null));
    }

}
