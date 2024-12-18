package com.example.laptopgiahuy2.controller;

import com.example.laptopgiahuy2.model.Category;
import com.example.laptopgiahuy2.model.Product;
import com.example.laptopgiahuy2.model.UserDtls;
import com.example.laptopgiahuy2.service.*;
import com.example.laptopgiahuy2.util.CommonUtil;
import io.micrometer.common.util.StringUtils;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
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
    private VNPAYService vnpayService;
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
    public HomeController(CategoryService categoryService, ProductService productService, UserDtlsService userDtlsService, CommonUtil commonUtil, BCryptPasswordEncoder passwordEncoder, CartService cartService, VNPAYService vnpayService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.userDtlsService = userDtlsService;
        this.commonUtil = commonUtil;
        this.passwordEncoder = passwordEncoder;
        this.cartService = cartService;
        this.vnpayService = vnpayService;
    }


    @GetMapping("/signing")
    public String login() {
        return "login";
    }
    @GetMapping("/register")
    public String register() {
        return "register";
    }
    @GetMapping("/")
    public String home(Model m, @RequestParam(value = "category",defaultValue = "") String category,
                       @RequestParam(name = "pageNo",defaultValue = "0")Integer pageNo,
                       @RequestParam(name = "pageSize",defaultValue = "8")Integer pageSize) {
        List<Category> categories = categoryService.getAllCategories();
        m.addAttribute("paramValue", category);
        m.addAttribute("categories", categories);

//		List<Product> products = productService.getAllActiveProducts(category);
//		m.addAttribute("products", products);
        Page<Product> page = null;
        page = productService.getActiveProductsPagination(pageNo, pageSize, category);
        List<Product> products = page.getContent();
        m.addAttribute("products", products);
        m.addAttribute("productsSize", products.size());

        m.addAttribute("pageNo", page.getNumber());
        m.addAttribute("pageSize", pageSize);
        m.addAttribute("totalElements", page.getTotalElements());
        m.addAttribute("totalPages", page.getTotalPages());
        m.addAttribute("isFirst", page.isFirst());
        m.addAttribute("isLast", page.isLast());
        return "product";
    }
    @GetMapping("/products")
    public String products(Model m, @RequestParam(value = "category", defaultValue = "") String category,
                           @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
                           @RequestParam(name = "pageSize", defaultValue = "12") Integer pageSize,
                           @RequestParam(defaultValue = "") String ch) {
        List<Category> categories = categoryService.getAllCategories();
        m.addAttribute("paramValue", category);
        m.addAttribute("categories", categories);

//		List<Product> products = productService.getAllActiveProducts(category);
//		m.addAttribute("products", products);
        Page<Product> page = null;
        if (StringUtils.isEmpty(ch)) {
            page = productService.getActiveProductsPagination(pageNo, pageSize, category);
        } else {
            page = productService.searchActiveProductPagination(pageNo, pageSize, category, ch);
        }

        List<Product> products = page.getContent();
        m.addAttribute("products", products);
        m.addAttribute("productsSize", products.size());

        m.addAttribute("pageNo", page.getNumber());
        m.addAttribute("pageSize", pageSize);
        m.addAttribute("totalElements", page.getTotalElements());
        m.addAttribute("totalPages", page.getTotalPages());
        m.addAttribute("isFirst", page.isFirst());
        m.addAttribute("isLast", page.isLast());

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
    public String searchProduct(@RequestParam String ch, Model m) {
        List<Product> searchProducts = productService.searchProducts(ch);
        m.addAttribute("products", searchProducts);
        List<Category> categories = categoryService.getAllCategories();
        m.addAttribute("categories", categories);
        return "product";

    }
    // Chuyển hướng người dùng đến cổng thanh toán VNPAY
    @PostMapping("/submitOrder")
    public String submidOrder(@RequestParam("amount") int orderTotal,
                              @RequestParam("orderInfo") String orderInfo,
                              HttpServletRequest request) {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnpayService.createOrder(request, orderTotal, orderInfo, baseUrl);
        return "redirect:" + vnpayUrl;
    }

    // Sau khi hoàn tất thanh toán, VNPAY sẽ chuyển hướng trình duyệt về URL này
    @GetMapping("/vnpay-payment-return")
    public String paymentCompleted(HttpServletRequest request, Model model) {
        int paymentStatus = vnpayService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);

        if (paymentStatus == 1) {
            return "/user/orderSuccess";
        }else {
            return "/user/orderFail";
        }
    }
}
