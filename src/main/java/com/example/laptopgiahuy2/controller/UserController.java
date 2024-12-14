package com.example.laptopgiahuy2.controller;

import com.example.laptopgiahuy2.model.Cart;
import com.example.laptopgiahuy2.model.Category;
import com.example.laptopgiahuy2.model.UserDtls;
import com.example.laptopgiahuy2.repository.CartRepository;
import com.example.laptopgiahuy2.service.CartService;
import com.example.laptopgiahuy2.service.CategoryService;
import com.example.laptopgiahuy2.service.UserDtlsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
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
    private CartService cartService;
    public UserController(UserDtlsService userDtlsService, CategoryService categoryService, CartService cartService) {
        this.userDtlsService = userDtlsService;
        this.categoryService = categoryService;
        this.cartService = cartService;
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

    @GetMapping("/addCart")
    public String addCart(@RequestParam Integer pid, @RequestParam Integer uid, HttpSession session) {
        Cart cart =cartService.saveCart(pid,uid);

        if (ObjectUtils.isEmpty(cart)) {
            session.setAttribute("errorMsg", "Product add to cart failed");
        } else {
            session.setAttribute("succMsg", "Product added to cart");
        }
        return "redirect:/product/"+pid;
    }
}
