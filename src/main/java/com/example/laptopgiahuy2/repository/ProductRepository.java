package com.example.laptopgiahuy2.repository;

import com.example.laptopgiahuy2.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
     List<Product> findByTrangthaiTrue();

    List<Product> findByDanhMuc(String danhMuc);
    List<Product> findByTensanphamContainingIgnoreCaseOrDanhMucContainingIgnoreCase(String ch1,String ch2);
}
