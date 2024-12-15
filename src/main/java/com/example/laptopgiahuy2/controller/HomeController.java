package com.example.laptopgiahuy2.controller;

import com.example.laptopgiahuy2.model.Category;
import com.example.laptopgiahuy2.model.Product;
import com.example.laptopgiahuy2.model.UserDtls;
import com.example.laptopgiahuy2.service.CartService;
import com.example.laptopgiahuy2.service.CategoryService;
import com.example.laptopgiahuy2.service.ProductService;
import com.example.laptopgiahuy2.service.UserDtlsService;
import com.example.laptopgiahuy2.util.CommonUtil;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
public class HomeController {
    private CategoryService categoryService;
    private ProductService productService;
    private UserDtlsService userDtlsService;
    private CommonUtil commonUtil;
    private BCryptPasswordEncoder passwordEncoder;
    private CartService cartService;
    @ModelAttribute
    public void getUserDetails(Principal principal, Model model) {
        if (principal != null) {
            String email = principal.getName();
            UserDtls userDtls= userDtlsService.getUserDtlsByEmail(email);
            model.addAttribute("userDtls", userDtls);
           Integer countCart= cartService.getCountCart(userDtls.getUserId());
           model.addAttribute("countCart", countCart);
        }
        List<Category>categoryList= categoryService.getCategoryByTrangThai();
        model.addAttribute("categoryList", categoryList);
    }
    public HomeController(CategoryService categoryService, ProductService productService, UserDtlsService userDtlsService, CommonUtil commonUtil, BCryptPasswordEncoder passwordEncoder, CartService cartService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.userDtlsService = userDtlsService;
        this.commonUtil = commonUtil;
        this.passwordEncoder = passwordEncoder;
        this.cartService = cartService;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }
    @GetMapping("/signing")
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
    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "forgot_password";
    }
    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam String email, HttpSession session,
                                        HttpServletRequest request)
            throws MessagingException, UnsupportedEncodingException {
        UserDtls userDtlsMail= userDtlsService.getUserDtlsByEmail(email);

        if (ObjectUtils.isEmpty(userDtlsMail)) {
            session.setAttribute("errorMsg", "Invalid email");
        } else {
            String resetToken = UUID.randomUUID().toString();
            userDtlsService.updateUserResetToken(email, resetToken);
            String url = CommonUtil.generateUrl(request) + "/reset-password?token=" + resetToken;

            Boolean sendMail = commonUtil.sendMail(url, email);

            if (sendMail) {
                session.setAttribute("succMsg", "Please check your email..Password Reset link sent");
            } else {
                session.setAttribute("errorMsg", "Somethong wrong on server ! Email not send");
            }
        }
        return "redirect:/forgot-password";
    }
    @GetMapping("/reset-password")
    public String showResetPassword(@RequestParam String token,Model m) {
        UserDtls userToken = userDtlsService.getUserByToken(token);
        if (userToken == null) {
            m.addAttribute("msg", "Your link is invalid or expired !!");
            return "message";
        }
        m.addAttribute("token", token);
        return "reset_password";
    }


    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String token, @RequestParam String password,
                                Model m) {
        UserDtls userByToken = userDtlsService.getUserByToken(token);
        if (userByToken == null) {
            m.addAttribute("errorMsg", "Your link is invalid or expired !!");
            return "message";
        } else {
            userByToken.setPassword(passwordEncoder.encode(password));
            userByToken.setResetToken(null);
            userDtlsService.updateUserDtls(userByToken);
            // session.setAttribute("succMsg", "Password change successfully");
            m.addAttribute("msg", "Password change successfully");
            return "message";
        }

    }
    @GetMapping("/search")
    public String search(@RequestParam String keyword, Model model) {
        List<Product> searchP = productService.searchProducts(keyword);
        model.addAttribute("products", searchP);
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "product";
    }
}
