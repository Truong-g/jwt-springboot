package com.spring.jwt.dto;


import lombok.Data;

@Data
public class OrderDetailsFormRequest {

    private int quantity;
    private Long productId;

}
