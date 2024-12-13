package com.example.laptopgiahuy2.service.Implement;

import com.example.laptopgiahuy2.model.Category;
import com.example.laptopgiahuy2.model.Product;
import com.example.laptopgiahuy2.repository.ProductRepository;
import com.example.laptopgiahuy2.service.ProductService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
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
        Product product = productRepository.findById(id).orElse(null);
        return product;
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

    @Override
    public Product updateProduct(Product product, MultipartFile image) {
        Product dbProduct = getProductById(product.getProductId());
        String imageName= image.isEmpty() ? "default.jpg" : image.getOriginalFilename();
        dbProduct.setTensanpham(product.getTensanpham());
        dbProduct.setSoluong(product.getSoluong());
        dbProduct.setHinhanh(imageName);
        dbProduct.setGia(product.getGia());
        dbProduct.setMota(product.getMota());
        dbProduct.setCauhinh(product.getCauhinh());
        dbProduct.setTrangthai(product.getTrangthai());
        dbProduct.setBaohanh(product.getBaohanh());
        dbProduct.setDanhMuc(product.getDanhMuc());
        dbProduct.setSale(product.getSale());
        int discount = (int) (product.getGia() * (product.getSale() / 100.0));
        int discountPrice = product.getGia() - discount;
        dbProduct.setGiaSale(discountPrice);
        Product savedProduct = productRepository.save(dbProduct);
        if (!ObjectUtils.isEmpty(savedProduct)) {

            if (!image.isEmpty()) {

                try {
                    File saveFile = new ClassPathResource("static/img").getFile();

                    Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "product_img" + File.separator
                            + image.getOriginalFilename());
                    Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return product;
        }
        return null;
    }

    @Override
    public List<Product> getActiveProducts(String category) {
        List<Product> activeProducts = null;
        if (ObjectUtils.isEmpty(category)){
            activeProducts=productRepository.findByTrangthaiTrue();
        }
        else {
            activeProducts=productRepository.findByDanhMuc(category);
        }
        return activeProducts;
    }
}
