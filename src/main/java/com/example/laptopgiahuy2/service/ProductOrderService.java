package com.example.laptopgiahuy2.service;

import com.example.laptopgiahuy2.model.OrderRequest;
import com.example.laptopgiahuy2.model.ProductOrder;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductOrderService {
    void saveProductOrder(Integer orderId, OrderRequest orderRequest) throws Exception;
    List<ProductOrder> getOrderByUserId(Integer userId);

    ProductOrder updateOrderStatus(Integer id, String status);
    List<ProductOrder> getAllOrders();
    ProductOrder getOrderById(String orderId);
    Page<ProductOrder> getAllOrdersPagination(Integer pageNo, Integer pageSize);
}
