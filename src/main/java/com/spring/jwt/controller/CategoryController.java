package com.spring.jwt.controller;

import com.spring.jwt.dto.CategoryFormRequest;
import com.spring.jwt.dto.CustomResponse;
import com.spring.jwt.entity.Category;
import com.spring.jwt.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<CustomResponse<List<Category>>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        CustomResponse<List<Category>> response = new CustomResponse<List<Category>>(HttpStatus.OK.value(), "success", categories);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Optional<Category>>> getCategory(@PathVariable Long id) {
        Optional<Category> category = categoryService.getCategory(id);
        CustomResponse<Optional<Category>> response = new CustomResponse<Optional<Category>>(HttpStatus.OK.value(), "success", category);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CustomResponse<Category>> addCategory(@RequestBody CategoryFormRequest request) {
        Category category = categoryService.addCategory(request);
        CustomResponse<Category> response = new CustomResponse<>(HttpStatus.OK.value(), "success", category);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<Category>> updateCategory(@PathVariable Long id, @RequestBody CategoryFormRequest request) {
        Category category = categoryService.updateCategory(id, request);
        CustomResponse<Category> response = new CustomResponse<>(HttpStatus.OK.value(), "success", category);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<String>> updateCategory(@PathVariable Long id) {
        categoryService.removeCategory(id);
        CustomResponse<String> response = new CustomResponse<>(HttpStatus.OK.value(), "success", null);
        return ResponseEntity.ok(response);
    }


}
