package com.example.laptopgiahuy2.repository;

import com.example.laptopgiahuy2.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    Cart findByUser_UserIdAndProduct_ProductId(Integer userId, Integer productId);

    Integer countByUser_UserId(Integer userId);

    List<Cart> findByUser_UserId(Integer userId);
}
