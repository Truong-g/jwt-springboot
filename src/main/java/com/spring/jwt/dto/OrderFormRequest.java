package com.spring.jwt.dto;


import lombok.Data;

import java.util.List;

@Data
public class OrderFormRequest {
    private String address;

    private String phone;

    private String note;

    private List<OrderDetailsFormRequest> products;
}
