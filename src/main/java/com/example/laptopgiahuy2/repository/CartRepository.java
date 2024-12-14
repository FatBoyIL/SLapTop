package com.example.laptopgiahuy2.repository;

import com.example.laptopgiahuy2.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    Cart findByUser_UserIdAndProduct_ProductId(Integer userId, Integer productId);
}
