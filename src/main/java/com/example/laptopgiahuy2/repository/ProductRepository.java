package com.example.laptopgiahuy2.repository;

import com.example.laptopgiahuy2.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findByTensanpham(String name);
    List<Product> findByTrangthaiTrue();
    List<Product> findByDanhMuc(String danhMuc);
    List<Product>findByTensanphamIgnoreCaseOrDanhMucContainingIgnoreCase(String ch, String ch2);
    Page<Product> findByTrangthaiTrue(Pageable pageable);
    Page<Product> findByDanhMuc(Pageable pageable,String category);
    Page<Product> findByTensanphamContainingIgnoreCaseOrDanhMucContainingIgnoreCase(String ch1,String ch2,Pageable ch3);
    Page<Product> findByTrangthaiTrueAndTensanphamContainingIgnoreCaseOrDanhMucContainingIgnoreCase(String ch1,String ch2,Pageable ch3);
}
