package com.spring.jwt.service.product;

import com.spring.jwt.dto.ProductFormRequest;
import com.spring.jwt.entity.Product;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> getAllProducts();

    Optional<Product> getProduct(Long productId);

    Product addProduct(ProductFormRequest product) throws IOException;

    Product updateProduct(ProductFormRequest product);

    String deleteProduct(Long productId);

}
