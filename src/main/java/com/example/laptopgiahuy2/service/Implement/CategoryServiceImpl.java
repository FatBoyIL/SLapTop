package com.example.laptopgiahuy2.service.Implement;


import com.example.laptopgiahuy2.model.Category;
import com.example.laptopgiahuy2.repository.CategoryRepository;
import com.example.laptopgiahuy2.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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
        return categoryRepository.existsCategoryByTendanhmuc(categoryName);
    }

    @Override
    public Boolean deleteCategory(int categoryId) {
        Category category= categoryRepository.findById(categoryId).orElse(null);
        if (!ObjectUtils.isEmpty(category))
        {
            categoryRepository.delete(category);
            return true;
        }
        return false;
    }

    @Override
    public Category getCategory(int id) {
        Category category = categoryRepository.findById(id).orElse(null);

        return category;
    }

    @Override
    public List<Category> getCategoryByTrangThai() {
        List<Category> categoryList = categoryRepository.findCategoryByTrangThaiTrue();
        return categoryList;
    }

    @Override
    public Page<Category> getCategoryPagination(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return categoryRepository.findAll(pageable);
    }


}
