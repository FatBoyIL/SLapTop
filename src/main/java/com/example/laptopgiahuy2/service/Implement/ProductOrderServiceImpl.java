package com.example.laptopgiahuy2.service.Implement;

import com.example.laptopgiahuy2.model.Cart;
import com.example.laptopgiahuy2.model.OrderAddress;
import com.example.laptopgiahuy2.model.OrderRequest;
import com.example.laptopgiahuy2.model.ProductOrder;
import com.example.laptopgiahuy2.repository.CartRepository;
import com.example.laptopgiahuy2.repository.ProductOrderRepository;
import com.example.laptopgiahuy2.service.ProductOrderService;
import com.example.laptopgiahuy2.util.OrderStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
@Service
public class ProductOrderServiceImpl implements ProductOrderService {
    private ProductOrderRepository productOrderRepository;
    private CartRepository cartRepository;

    public ProductOrderServiceImpl(ProductOrderRepository productOrderRepository, CartRepository cartRepository) {
        this.productOrderRepository = productOrderRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public void saveProductOrder(Integer userId, OrderRequest orderRequest) {
        List<Cart> cartList = cartRepository.findByUser_UserId(userId);
        for (Cart cart : cartList) {
            //hang khach dat
            ProductOrder productOrder = new ProductOrder();
            productOrder.setOrderId("DH"+ UUID.randomUUID().toString());
            productOrder.setOrderDate(new Date());
            productOrder.setProduct(cart.getProduct());
            productOrder.setPrice(cart.getProduct().getGiaSale());
            productOrder.setUserDtls(cart.getUser());
            productOrder.setStatus(OrderStatus.DANG_XULY.name());
            productOrder.setPaymentType(orderRequest.getPaymentType());
            productOrder.setQuantity(cart.getQuantity());

            //thong tin khach hang
            OrderAddress orderAddress = new OrderAddress();
            orderAddress.setGuestName(orderRequest.getGuestName());
            orderAddress.setAddress(orderRequest.getAddress());
            orderAddress.setSdt(orderRequest.getSdt());
            orderAddress.setEmail(orderRequest.getEmail());
            orderAddress.setGhichu(orderRequest.getGhichu());

            productOrder.setOrderAddress(orderAddress);

            productOrderRepository.save(productOrder);
        }

    }
}
