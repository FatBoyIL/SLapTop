package com.example.laptopgiahuy2.controller;

import com.example.laptopgiahuy2.model.Category;
import com.example.laptopgiahuy2.model.UserDtls;
import com.example.laptopgiahuy2.service.CategoryService;
import com.example.laptopgiahuy2.service.UserDtlsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserDtlsService userDtlsService;
    private CategoryService categoryService;
    public UserController(UserDtlsService userDtlsService, CategoryService categoryService) {
        this.userDtlsService = userDtlsService;
        this.categoryService = categoryService;
    }
    @ModelAttribute
    public void getUserDetails(Principal principal, Model model) {
        if (principal != null) {
            String email = principal.getName();
            UserDtls userDtls= userDtlsService.getUserDtlsByEmail(email);
            model.addAttribute("userDtls", userDtls);
        }
        List<Category> categoryList= categoryService.getCategoryByTrangThai();
        model.addAttribute("categoryList", categoryList);
    }
    @GetMapping("/")
    public String home() {
        return "user/home";
    }
}
