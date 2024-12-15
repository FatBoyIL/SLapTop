package com.example.laptopgiahuy2.service;

import com.example.laptopgiahuy2.model.OrderRequest;
import org.springframework.stereotype.Service;

@Service
public interface ProductOrderService {
    void saveProductOrder(Integer orderId, OrderRequest orderRequest);
}
