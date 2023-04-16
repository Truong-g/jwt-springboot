package com.spring.jwt.controller;


import com.spring.jwt.dto.CategoryFormRequest;
import com.spring.jwt.dto.CustomResponse;
import com.spring.jwt.dto.ProductFormRequest;
import com.spring.jwt.entity.Category;
import com.spring.jwt.entity.Product;
import com.spring.jwt.service.product.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;


    @GetMapping
    public ResponseEntity<CustomResponse<List<Product>>> getAllproducts() {
        List<Product> products = productService.getAllProducts();
        CustomResponse<List<Product>> response = new CustomResponse<List<Product>>(HttpStatus.OK.value(), "success", products);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Optional<Product>>> getProduct(@PathVariable Long id) {
        Optional<Product> product = productService.getProduct(id);
        CustomResponse<Optional<Product>> response = new CustomResponse<Optional<Product>>(HttpStatus.OK.value(), "success", product);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CustomResponse<Product>> addProduct(@Valid @ModelAttribute ProductFormRequest request) throws IOException {
        Product product = productService.addProduct(request);
        CustomResponse<Product> response = new CustomResponse<>(HttpStatus.OK.value(), "success", product);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<Product>> updateProduct(@PathVariable Long id, @Valid @ModelAttribute ProductFormRequest request) throws IOException {
        Product product = productService.updateProduct(id, request);
        CustomResponse<Product> response = new CustomResponse<>(HttpStatus.OK.value(), "success", product);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<String>> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        CustomResponse<String> response = new CustomResponse<>(HttpStatus.OK.value(), "success", null);
        return ResponseEntity.ok(response);
    }

}
