package com.example.laptopgiahuy2.controller;

import com.example.laptopgiahuy2.model.*;
import com.example.laptopgiahuy2.repository.CartRepository;
import com.example.laptopgiahuy2.service.*;
import com.example.laptopgiahuy2.util.CommonUtil;
import com.example.laptopgiahuy2.util.OrderStatus;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserDtlsService userDtlsService;
    private CategoryService categoryService;
    private CartService cartService;
    private ProductOrderService productOrderService;
    private CommonUtil commonUtil;
    private PasswordEncoder passwordEncoder;
    private ProductService productService;
    private VNPAYService vnpayService;
    public UserController(UserDtlsService userDtlsService, CategoryService categoryService, CartService cartService, ProductOrderService productOrderService, CommonUtil commonUtil, PasswordEncoder passwordEncoder, ProductService productService, VNPAYService vnpayService) {
        this.userDtlsService = userDtlsService;
        this.categoryService = categoryService;
        this.cartService = cartService;
        this.productOrderService = productOrderService;
        this.commonUtil = commonUtil;
        this.passwordEncoder = passwordEncoder;
        this.productService = productService;
        this.vnpayService = vnpayService;
    }
    private UserDtls getLoggedUser(Principal principal) {
        String email = principal.getName();
        UserDtls userDtls= userDtlsService.getUserDtlsByEmail(email);
        return userDtls;
    }
    @ModelAttribute
    public void getUserDetails(Principal principal, Model model) {
        if (principal != null) {
            String email = principal.getName();
            UserDtls userDtls= userDtlsService.getUserDtlsByEmail(email);
            model.addAttribute("userDtls", userDtls);
            Integer countCart= cartService.getCountCart(userDtls.getUserId());
            model.addAttribute("countCart", countCart);
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
    @GetMapping("/cart")
    public String loadCartPage(Principal principal, Model model) {
        UserDtls userDtls = getLoggedUser(principal);
        List<Cart> cartList= cartService.getCartByUserId(userDtls.getUserId());
        model.addAttribute("cartList", cartList);
        if (cartList.size() > 0) {
            Integer totalPrice= cartList.get(cartList.size()-1).getTotalOrderPrice();
            model.addAttribute("totalOrderPrice", totalPrice);
        }
        return "user/cart";
    }
    @GetMapping("/cartQuantityUpdate")
    public String updateCartQuantity(@RequestParam String sy, @RequestParam Integer cid) {
      cartService.updateQuantity(sy,cid);
      return "redirect:/user/cart";
    }


    @GetMapping("/orders")
    public String loadOrders(Principal p, Model m) {
        UserDtls user = getLoggedUser(p);
        List<Cart> carts = cartService.getCartByUserId(user.getUserId());
        m.addAttribute("carts", carts);
        if (carts.size() > 0) {
            Integer orderPrice = carts.get(carts.size() - 1).getTotalOrderPrice();
            double vat = (carts.get(carts.size() - 1).getTotalOrderPrice()) * 0.1;
            Integer totalOrderPrice = (int) Math.round((carts.get(carts.size() - 1).getTotalOrderPrice() + 50000) + vat );
            m.addAttribute("orderPrice", orderPrice);
            m.addAttribute("totalOrderPrice", totalOrderPrice);
        }
        return "user/order";
    }

    @PostMapping("/save-order")
    public String saveOrders(@ModelAttribute OrderRequest orderRequest,Principal principal) throws Exception {
        UserDtls userDtls = getLoggedUser(principal);
        if (orderRequest.getPaymentType().equals("ONLINE")){
            productOrderService.saveProductOrder(userDtls.getUserId(),orderRequest);
            return "/user/createOrder";
        }
        productOrderService.saveProductOrder(userDtls.getUserId(),orderRequest);

        return "/user/success";
    }

    @GetMapping("/user-orders")
        public String myOrder(Model model,Principal principal) {
        UserDtls userDtls = getLoggedUser(principal);
       List<ProductOrder> productOrders= productOrderService.getOrderByUserId(userDtls.getUserId());
        model.addAttribute("productOrders", productOrders);
        return "/user/my_orders";
    }
    @GetMapping("/update-status")
    public String updateOrderStatus(@RequestParam Integer id, @RequestParam Integer st, HttpSession session) throws MessagingException, UnsupportedEncodingException {

        OrderStatus[] values = OrderStatus.values();
        String status = null;
        for (OrderStatus orderSt : values) {
            if (orderSt.getId().equals(st)) {
                status = orderSt.getName();
            }
        }
        ProductOrder updateOrder = productOrderService.updateOrderStatus(id, status);
        commonUtil.sendMailProductOrder(updateOrder,status);
        if (!ObjectUtils.isEmpty(updateOrder)) {
            session.setAttribute("succMsg", "Hủy Hàng Thành Công");
        } else {
            session.setAttribute("errorMsg", "Không Hủy Hàng Được");
        }
        return "redirect:/user/user-orders";
    }
    @GetMapping("/profile")
    public String profile() {
        return "user/profile";
    }
    @PostMapping("/update-profile")
    public String updateProfile(@ModelAttribute UserDtls userDtls, @RequestParam MultipartFile img,HttpSession session) {
      UserDtls userDtls1= userDtlsService.updateUserProfile(userDtls,img);
        if (!ObjectUtils.isEmpty(userDtls1)) {
            session.setAttribute("succMsg", "Cập Nhật Profile Thành Công");
        } else {
            session.setAttribute("errorMsg", "Không Cập Nhật Được");
        }
        return "redirect:/user/profile";
    }
    @PostMapping("/change-password")
    public String changePassword(@RequestParam String oldPassword, @RequestParam String newPassword, Principal p,HttpSession session)
    {
        UserDtls userDtls = getLoggedUser(p);
        boolean matches = passwordEncoder.matches(oldPassword,userDtls.getPassword());

        if (matches) {
            String encodePassword = passwordEncoder.encode(newPassword);
            userDtls.setPassword(encodePassword);
            UserDtls updateUser = userDtlsService.updateUserDtls(userDtls);
            if (ObjectUtils.isEmpty(updateUser)) {
                session.setAttribute("errorMsg", "Mật Khẩu Chưa Được Cập Nhật");
            } else {
                session.setAttribute("succMsg", "Mật Khẩu Đã Được Cập Nhật");
            }
        } else {
            session.setAttribute("errorMsg", "Mật Khẩu Chưa Chính Xác");
        }

        return "redirect:/user/profile";
    }


}
