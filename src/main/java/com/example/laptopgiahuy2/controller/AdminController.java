package com.example.laptopgiahuy2.controller;

import com.example.laptopgiahuy2.model.Category;
import com.example.laptopgiahuy2.model.Product;
import com.example.laptopgiahuy2.service.CategoryService;
import com.example.laptopgiahuy2.service.ProductService;
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
@RequestMapping("/admin")
public class AdminController {
    private CategoryService categoryService;
    private ProductService productService;

    public AdminController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping("/")
    public String index() {
        return "admin/index";
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
    public String loadProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
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
            session.setAttribute("errorMsg", "Ma giam gia loi");
        }else {
            Product savedProduct = productService.updateProduct(product, image);
            if (!ObjectUtils.isEmpty(savedProduct)) {
                session.setAttribute("succMsg", "Sua san pham thanh cong");
            }
            else {
                session.setAttribute("errorMsg", "Khong sua duoc");
            }

        }
        return "redirect:/admin/products";
    }
}
