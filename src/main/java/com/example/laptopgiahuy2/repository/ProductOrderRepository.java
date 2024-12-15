package com.example.laptopgiahuy2.repository;

import com.example.laptopgiahuy2.model.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Integer> {
    List<ProductOrder> findByUserDtls_UserId(Integer userId);
    ProductOrder findByOrderId(String orderId);
}
