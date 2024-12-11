package com.example.laptopgiahuy2.service;

import com.example.laptopgiahuy2.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface CategoryService {
    public Category saveCategory(Category category);
    public List<Category> getAllCategories();
    public Boolean existCategory(String categoryName);
    public Boolean deleteCategory(int id);
    public Category getCategory(int id);


}
