package com.example.laptopgiahuy2.service;

import com.example.laptopgiahuy2.model.Cart;
import com.example.laptopgiahuy2.model.UserDtls;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartService {
    Cart saveCart(Integer productId,Integer userId);
    List<Cart> getCartByUserId(Integer userId);
}
