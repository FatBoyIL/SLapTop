package com.example.laptopgiahuy2.service.Implement;

import com.example.laptopgiahuy2.model.Category;
import com.example.laptopgiahuy2.model.Product;
import com.example.laptopgiahuy2.repository.ProductRepository;
import com.example.laptopgiahuy2.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Boolean deleteProduct(int id) {
        Product product= productRepository.findById(id).orElse(null);
        if (!ObjectUtils.isEmpty(product))
        {
            productRepository.delete(product);
            return true;
        }
        return false;
    }
}
