package com.example.laptopgiahuy2.repository;

import com.example.laptopgiahuy2.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    public Boolean existsCategoryByTendanhmuc(String categoryName);
}
