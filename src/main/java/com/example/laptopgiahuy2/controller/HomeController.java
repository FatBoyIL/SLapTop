package com.example.laptopgiahuy2.controller;

import com.example.laptopgiahuy2.model.Category;
import com.example.laptopgiahuy2.model.Product;
import com.example.laptopgiahuy2.model.UserDtls;
import com.example.laptopgiahuy2.service.CategoryService;
import com.example.laptopgiahuy2.service.ProductService;
import com.example.laptopgiahuy2.service.UserDtlsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
public class HomeController {
    private CategoryService categoryService;
    private ProductService productService;
    private UserDtlsService userDtlsService;

    public HomeController(CategoryService categoryService, ProductService productService, UserDtlsService userDtlsService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.userDtlsService = userDtlsService;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }
    @GetMapping("/signin")
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
    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute UserDtls userDtls, @RequestParam("img") MultipartFile file, HttpSession session) throws IOException {
        String imageName = file.isEmpty() ? "default.jpg" : file.getOriginalFilename();
        userDtls.setProfileImage(imageName);
        UserDtls saveUser=userDtlsService.saveUserDtls(userDtls);

        if (!ObjectUtils.isEmpty(saveUser)) {
            if (!file.isEmpty()) {
                File saveFile = new ClassPathResource("static/img").getFile();

                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "profile_img" + File.separator
                        + file.getOriginalFilename());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            }
            session.setAttribute("succMsg", "Đăng ký thành công ");
        } else {
            session.setAttribute("errorMsg", "Đăng ký thất bại hãy xem lại");
        }
        return "redirect:/register";
    }

}
