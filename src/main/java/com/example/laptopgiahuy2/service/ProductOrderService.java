package com.example.laptopgiahuy2.service;

import com.example.laptopgiahuy2.model.OrderRequest;
import com.example.laptopgiahuy2.model.ProductOrder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductOrderService {
    void saveProductOrder(Integer orderId, OrderRequest orderRequest);
    List<ProductOrder> getOrderByUserId(Integer userId);

    ProductOrder updateOrderStatus(Integer id, String status);
    List<ProductOrder> getAllOrders();
}
