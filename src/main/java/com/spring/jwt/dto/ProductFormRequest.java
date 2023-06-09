package com.spring.jwt.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductFormRequest implements Serializable {
    @NotNull
    private String name;
    @NotNull
    private Long catId;
    @NotNull
    private String description;
    @NotNull
    private double price;
    @NotNull
    private double priceSale;
    private MultipartFile imageFile;
}
