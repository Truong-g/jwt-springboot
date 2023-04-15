package com.spring.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductFormRequest implements Serializable {
    private String name;
    private Long catId;
    private String description;
    private double price;
    private MultipartFile imageFile;
}
