package com.example.laptopgiahuy2.repository;

import com.example.laptopgiahuy2.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    public Boolean existsCategoryByTendanhmuc(String categoryName);
    public List<Category> findCategoryByTrangThaiTrue();
}
