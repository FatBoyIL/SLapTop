package com.example.laptopgiahuy2.service;

import com.example.laptopgiahuy2.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface ProductService{
    public Product saveProduct(Product product);
    public List<Product> getAllProducts();
    public Product getProductById(int id);
    public Boolean deleteProduct(int id);
    public Product updateProduct(Product product, MultipartFile file);
    public List<Product> getActiveProducts(String category);
}
