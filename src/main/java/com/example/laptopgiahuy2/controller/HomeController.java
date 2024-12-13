package com.example.laptopgiahuy2.controller;

import com.example.laptopgiahuy2.model.Category;
import com.example.laptopgiahuy2.model.Product;
import com.example.laptopgiahuy2.service.CategoryService;
import com.example.laptopgiahuy2.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {
    private CategoryService categoryService;
    private ProductService productService;

    public HomeController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/register")
    public String register() {
        return "register";
    }
    @GetMapping("/products")
    public String products(Model m, @RequestParam(value = "category",defaultValue = "") String category) {
        List<Product> products = productService.getActiveProducts(category);
        List<Category> categories = categoryService.getCategoryByTrangThai();
        m.addAttribute("products", products);
        m.addAttribute("categories", categories);
        return "product";
    }
    @GetMapping("/product/{id}")
    public String product(@PathVariable int id, Model m) {
        Product product= productService.getProductById(id);
        m.addAttribute("product", product);
        return "view_product";
    }

}
