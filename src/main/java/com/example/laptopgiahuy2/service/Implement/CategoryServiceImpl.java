package com.example.laptopgiahuy2.service.Implement;


import com.example.laptopgiahuy2.model.Category;
import com.example.laptopgiahuy2.repository.CategoryRepository;
import com.example.laptopgiahuy2.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Boolean existCategory(String categoryName) {
        return categoryRepository.existsCategoryByName(categoryName);
    }
}
