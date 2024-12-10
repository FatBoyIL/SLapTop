package com.example.laptopgiahuy2.controller;

import com.example.laptopgiahuy2.model.Category;
import com.example.laptopgiahuy2.service.CategoryService;
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

@Controller
@RequestMapping("/admin")
public class AdminController {
    private CategoryService categoryService;

    public AdminController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String index() {
        return "admin/index";
    }
    @GetMapping("/loadAddProduct")
    public String loadAddProduct() {
        return "admin/add_product";
    }
    @GetMapping("/category")
    public String category(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/category";
    }

    @PostMapping("/saveCategory")
    public String saveCategory(@ModelAttribute Category category, HttpSession session, @RequestParam("file") MultipartFile file) throws IOException {

        String imageName= file != null ? file.getOriginalFilename() : "default.jpg";
        category.setImageName(imageName);

        Boolean existCategory= categoryService.existCategory(category.getName());
        if (existCategory) {
            session.setAttribute("errorMsg", "Ten danh muc da tung ton tai");
        }else {
            File saveFile = new ClassPathResource("static/img").getFile();

            Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "category_img" + File.separator
                    + file.getOriginalFilename());

            // System.out.println(path);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
           Category saveCategory = categoryService.saveCategory(category);
            if (ObjectUtils.isEmpty(saveCategory)) {
                session.setAttribute("errorMsg", "Kiem tra lai ! Danh muc chua duoc luu");
            }
            else {
                session.setAttribute("succMsg", "Luu thanh cong");
            }
        }

        return "redirect:/admin/category";
    }

}
