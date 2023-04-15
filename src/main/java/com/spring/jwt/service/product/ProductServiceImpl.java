package com.spring.jwt.service.product;

import com.spring.jwt.dto.ProductFormRequest;
import com.spring.jwt.entity.Category;
import com.spring.jwt.entity.Product;
import com.spring.jwt.entity.User;
import com.spring.jwt.exception.BaseException;
import com.spring.jwt.repository.CategoryRepository;
import com.spring.jwt.repository.ProductRepository;
import com.spring.jwt.repository.UserRepository;
import com.spring.jwt.service.category.CategoryService;
import com.spring.jwt.service.files.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FileService fileService;
    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    @Override
    public Optional<Product> getProduct(Long productId) {
        return productRepository.findById(productId);
    }

    @Override
    public Product addProduct(ProductFormRequest product) throws IOException {
        Product product1 = new Product();
        product1.setCategory(categoryService.getCategory(product.getCatId()).orElseThrow());
        product1.setName(product.getName());
        product1.setDescription(product.getDescription());
        product1.setPrice(product.getPrice());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());
        product1.setUser(user);
        String pathImage = fileService.store(product.getImageFile());
        product1.setImage(pathImage);
        return productRepository.save(product1);
    }

    @Override
    public Product updateProduct(ProductFormRequest product) {
        return null;
    }

    @Override
    public void deleteProduct(Long productId) {
        productRepository.delete(getProduct(productId).orElseThrow(() -> {
            throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Failed");
        }));
    }

}
