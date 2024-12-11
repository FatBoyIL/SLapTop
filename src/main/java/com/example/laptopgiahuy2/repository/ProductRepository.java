package com.example.laptopgiahuy2.repository;

import com.example.laptopgiahuy2.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
