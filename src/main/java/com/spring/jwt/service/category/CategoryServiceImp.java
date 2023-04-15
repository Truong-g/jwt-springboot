package com.spring.jwt.service.category;

import com.spring.jwt.dto.CategoryFormRequest;
import com.spring.jwt.entity.Category;
import com.spring.jwt.entity.User;
import com.spring.jwt.exception.BaseException;
import com.spring.jwt.repository.CategoryRepository;
import com.spring.jwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImp implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
    @Override
    public Optional<Category> getCategory(Long catId) {
        return categoryRepository.findById(catId);
    }

    @Override
    public Category addCategory(CategoryFormRequest category) {
        Category category1 = new Category();
        category1.setName(category.getName());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());
        category1.setUser(user);
        return categoryRepository.save(category1);
    }
    @Override
    public Category updateCategory(Long catId, CategoryFormRequest category) {
        Category category1 = getCategory(catId).orElseThrow();
        category1.setName(category.getName());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());
        category1.setUser(user);
        return categoryRepository.save(category1);
    }

    @Override
    public void removeCategory(Long catId) {
         categoryRepository.delete(getCategory(catId).orElseThrow(() -> {
             throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Failed");
         }));
    }
}
