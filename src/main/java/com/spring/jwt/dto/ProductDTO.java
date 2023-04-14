package com.spring.jwt.dto;

import com.spring.jwt.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private Category category;
    private Timestamp createdAt;
    private Timestamp updatedAt;

}
