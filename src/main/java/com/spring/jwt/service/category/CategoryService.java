package com.spring.jwt.service.category;

import com.spring.jwt.dto.CategoryFormRequest;
import com.spring.jwt.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> getAllCategories();
    Optional<Category> getCategory(Long catId);
    Category addCategory(CategoryFormRequest category);
    Category updateCategory(Long catId, CategoryFormRequest category);
    void removeCategory(Long catId);

}
