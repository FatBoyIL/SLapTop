package com.example.laptopgiahuy2.controller;

import com.example.laptopgiahuy2.model.*;
import com.example.laptopgiahuy2.service.CategoryService;
import com.example.laptopgiahuy2.service.ProductOrderService;
import com.example.laptopgiahuy2.service.ProductService;
import com.example.laptopgiahuy2.service.UserDtlsService;
import com.example.laptopgiahuy2.util.OrderStatus;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
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
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private CategoryService categoryService;
    private ProductService productService;
    private UserDtlsService userDtlsService;
    private ProductOrderService productOrderService;
    public AdminController(CategoryService categoryService, ProductService productService, UserDtlsService userDtlsService, ProductOrderService productOrderService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.userDtlsService = userDtlsService;
        this.productOrderService = productOrderService;
    }
    @ModelAttribute
    public void getUserDetails(Principal principal, Model model) {
        if (principal != null) {
            String email = principal.getName();
            UserDtls userDtls= userDtlsService.getUserDtlsByEmail(email);
            model.addAttribute("userDtls", userDtls);
        }
        List<Category>categoryList= categoryService.getCategoryByTrangThai();
        model.addAttribute("categoryList", categoryList);
    }
    @GetMapping("/")
    public String index() {
        return "admin/index";
    }
    @GetMapping("/category")
    public String category(Model m, @RequestParam(value = "category",defaultValue = "") String category,
                           @RequestParam(name = "pageNo",defaultValue = "0")Integer pageNo,
                           @RequestParam(name = "pageSize",defaultValue = "5")Integer pageSize) {
        Page<Category> page = categoryService.getCategoryPagination(pageNo, pageSize);
        List<Category> categories = page.getContent();
        m.addAttribute("categories", categories);
        m.addAttribute("pageNo", page.getNumber());
        m.addAttribute("pageSize", pageSize);
        m.addAttribute("totalElements", page.getTotalElements());
        m.addAttribute("totalPages", page.getTotalPages());
        m.addAttribute("isFirst", page.isFirst());
        m.addAttribute("isLast", page.isLast());
        //        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/category";
    }

    @PostMapping("/saveCategory")
    public String saveCategory(@ModelAttribute Category category, HttpSession session, @RequestParam("file") MultipartFile file) throws IOException {

        String imageName= file != null ? file.getOriginalFilename() : "default.jpg";
        category.setImageName(imageName);

        Boolean existCategory= categoryService.existCategory(category.getTendanhmuc());
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
                session.setAttribute("succMsg", "Luu danh muc thanh cong");
            }
        }

        return "redirect:/admin/category";
    }
    @GetMapping("/deleteCategory/{id}")
    public String deleteCategory(@PathVariable("id") Integer id, HttpSession session) {

        Boolean deleteCategory= categoryService.deleteCategory(id);
        if (deleteCategory) {
            session.setAttribute("succMsg", "Xoa danh muc thanh cong");
        }
        else {
            session.setAttribute("errorMsg", "Khong xoa duoc");
        }
        return "redirect:/admin/category";
    }
    @GetMapping("/loadEditCategory/{id}")
    public String loadEditCategory(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("category", categoryService.getCategory(id));
        return "admin/edit_category";
    }
    @PostMapping("/updateCategory")
    public String updateCategory(@ModelAttribute Category category, @RequestParam("file") MultipartFile file,
                                 HttpSession session) throws IOException {

        Category oldCategory = categoryService.getCategory(category.getDanhMucId());
        String imageName = file.isEmpty() ? oldCategory.getImageName() : file.getOriginalFilename();

        if (!ObjectUtils.isEmpty(category)) {
            oldCategory.setTendanhmuc(category.getTendanhmuc());
            oldCategory.setTrangThai(category.getTrangThai());
            oldCategory.setImageName(imageName);
        }

        Category updateCategory = categoryService.saveCategory(oldCategory);

        if (!ObjectUtils.isEmpty(updateCategory)) {

            if (!file.isEmpty()) {
                File saveFile = new ClassPathResource("static/img").getFile();

                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "category_img" + File.separator
                        + file.getOriginalFilename());

                // System.out.println(path);
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            }

            session.setAttribute("succMsg", "Danh muc cap nhat thanh cong");
        } else {
            session.setAttribute("errorMsg", "Khong cap nhat duoc danh muc");
        }

        return "redirect:/admin/category";
    }

    @GetMapping("/loadAddProduct")
    public String loadAddProduct(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("category", categories);
        return "admin/add_product";
    }
    @PostMapping("/saveProduct")
    public String saveProduct(@ModelAttribute Product product, HttpSession session,@RequestParam("file") MultipartFile image) throws IOException {
        String imageName= image.isEmpty() ? "default.jpg" : image.getOriginalFilename();
        product.setHinhanh(imageName);
        product.setSale(0);
        product.setGiaSale(product.getGia());
        Product savedProduct = productService.saveProduct(product);
        if (!ObjectUtils.isEmpty(savedProduct)) {
            File saveFile = new ClassPathResource("static/img").getFile();

            Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "product_img" + File.separator
                    + image.getOriginalFilename());

            // System.out.println(path);
            Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            session.setAttribute("succMsg", "Da luu san pham");
        }
        else {
            session.setAttribute("errorMsg", "Khong luu duoc san pham");
        }
        return "redirect:/admin/products";
    }

    @GetMapping("/products")
    public String loadProducts(@RequestParam(defaultValue = "") String srch,
                               @RequestParam(name = "pageNo",defaultValue = "0")Integer pageNo,
                               @RequestParam(name = "pageSize",defaultValue = "5")Integer pageSize,Model m) {

        Page<Product> page = null;
        if (srch != null && srch.length() > 0) {
            page = productService.searchProductsPagination(srch, pageSize,pageNo);
        } else {
            page = productService.getAllProductsPagination(pageNo, pageSize);
        }
        m.addAttribute("products", page.getContent());

        m.addAttribute("pageNo", page.getNumber());
        m.addAttribute("pageSize", pageSize);
        m.addAttribute("totalElements", page.getTotalElements());
        m.addAttribute("totalPages", page.getTotalPages());
        m.addAttribute("isFirst", page.isFirst());
        m.addAttribute("isLast", page.isLast());

        return "admin/products";
    }
    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable("id") Integer id, HttpSession session) {
        Boolean deleteProduct= productService.deleteProduct(id);
        if (deleteProduct) {
            session.setAttribute("succMsg", "Xoa san pham thanh cong");
        }
        else {
            session.setAttribute("errorMsg", "Khong xoa duoc");
        }
        return "redirect:/admin/products";
    }
    @GetMapping("/editProduct/{id}")
    public String loadEditProduct(@PathVariable("id") Integer id, Model model) {
        Product product = productService.getProductById(id);
        List<Category> category = categoryService.getAllCategories();
        model.addAttribute("product", product);
        model.addAttribute("category", category);
        return "admin/edit_product";
    }
    @PostMapping("/updateProduct")
    public String updateProduct(@ModelAttribute Product product,@RequestParam("file") MultipartFile image,HttpSession session) {
        if (product.getSale()<0 || product.getSale()>90){
            session.setAttribute("errorMsg", "Mã Giảm Lỗi");
        }else {
            Product savedProduct = productService.updateProduct(product, image);
            if (!ObjectUtils.isEmpty(savedProduct)) {
                session.setAttribute("succMsg", "Sửa sản phẩm thành công");
            }
            else {
                session.setAttribute("errorMsg", "Không Sửa Được Sản Phẩm");
            }

        }
        return "redirect:/admin/products";
    }
    @GetMapping("/users")
    public String getAllUserDtls(Model model) {
        List<UserDtls> userDtlsList = userDtlsService.getAllUserDtls("ROLE_USER");
        model.addAttribute("userDtlsList", userDtlsList);
        return "admin/users";
    }
    @GetMapping("/updateSts")
    public String updateUserDtlsActive(@RequestParam Boolean active,@RequestParam Integer id, HttpSession session) {
        Boolean f = userDtlsService.updateUserDtlsActicve(id,active);
        if (f){
            session.setAttribute("succMsg", "Mở Khóa User Thành Công");
        }
        else {
            session.setAttribute("errorMsg", "Không Thể Mở Khóa");
        }
        return "redirect:/admin/users";
    }
    @GetMapping("/orders")
    public String loadOrders(Model m,@RequestParam(defaultValue = "") String srch,
                             @RequestParam(name = "pageNo",defaultValue = "0")Integer pageNo,
                             @RequestParam(name = "pageSize",defaultValue = "5")Integer pageSize) {
        Page<ProductOrder> page = productOrderService.getAllOrdersPagination(pageNo,pageSize);
        m.addAttribute("orders", page.getContent());
        m.addAttribute("srch", false);
        m.addAttribute("pageNo", page.getNumber());
        m.addAttribute("pageSize", pageSize);
        m.addAttribute("totalElements", page.getTotalElements());
        m.addAttribute("totalPages", page.getTotalPages());
        m.addAttribute("isFirst", page.isFirst());
        m.addAttribute("isLast", page.isLast());
        return "admin/orders";
    }
    @PostMapping("/update-order-status")
    public String updateOrderStatus(@RequestParam Integer id, @RequestParam Integer st, HttpSession session) {

        OrderStatus[] values = OrderStatus.values();
        String status = null;

        for (OrderStatus orderSt : values) {
            if (orderSt.getId().equals(st)) {
                status = orderSt.getName();
            }
        }

        ProductOrder updateOrder = productOrderService.updateOrderStatus(id, status);

        if (!ObjectUtils.isEmpty(updateOrder)) {
            session.setAttribute("succMsg", "Status Updated");
        } else {
            session.setAttribute("errorMsg", "status not updated");
        }
        return "redirect:/admin/orders";
    }
    @GetMapping("search-order")
    public String searchProduct(@RequestParam String prderId, Model model, HttpSession session,
                                @RequestParam(name = "pageNo",defaultValue = "0")Integer pageNo,
                                @RequestParam(name = "pageSize",defaultValue = "5")Integer pageSize) {
        if (prderId!=null&&prderId.length()>0) {
            ProductOrder order = productOrderService.getOrderById(prderId.trim());
            if (ObjectUtils.isEmpty(order)) {
                session.setAttribute("errorMsg", "Mã đơn hàng chưa đúng");
                model.addAttribute("orderDtls", null);
            } else {
                model.addAttribute("orderDtls", order);
            }
            model.addAttribute("srch", true);
        }else {
            Page<ProductOrder> page = productOrderService.getAllOrdersPagination(pageNo,pageSize);
            model.addAttribute("orders", page);
            model.addAttribute("srch", false);
            model.addAttribute("pageNo", page.getNumber());
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("totalElements", page.getTotalElements());
            model.addAttribute("totalPages", page.getTotalPages());
            model.addAttribute("isFirst", page.isFirst());
            model.addAttribute("isLast", page.isLast());
        }

        return "admin/orders";
    }

}
