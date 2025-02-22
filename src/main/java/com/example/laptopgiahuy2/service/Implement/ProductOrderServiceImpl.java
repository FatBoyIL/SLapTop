package com.example.laptopgiahuy2.service.Implement;

import com.example.laptopgiahuy2.model.*;
import com.example.laptopgiahuy2.repository.CartRepository;
import com.example.laptopgiahuy2.repository.ProductOrderRepository;
import com.example.laptopgiahuy2.repository.ProductRepository;
import com.example.laptopgiahuy2.service.ProductOrderService;
import com.example.laptopgiahuy2.util.CommonUtil;
import com.example.laptopgiahuy2.util.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public class ProductOrderServiceImpl implements ProductOrderService {
    private ProductOrderRepository productOrderRepository;
    private CartRepository cartRepository;
    private CommonUtil commonUtil;
    private ProductRepository productRepository;
    public ProductOrderServiceImpl(ProductOrderRepository productOrderRepository, CartRepository cartRepository, CommonUtil commonUtil, ProductRepository productRepository) {
        this.productOrderRepository = productOrderRepository;
        this.cartRepository = cartRepository;
        this.commonUtil = commonUtil;
        this.productRepository = productRepository;
    }

    @Override
    public void saveProductOrder(Integer userId, OrderRequest orderRequest) throws Exception{
        List<Cart> cartList = cartRepository.findByUser_UserId(userId);
        for (Cart cart : cartList) {
            //hang khach dat
            ProductOrder productOrder = new ProductOrder();
            productOrder.setOrderId("DH"+ UUID.randomUUID().toString());
            productOrder.setOrderDate(LocalDate.now());
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

            ProductOrder productOrder1 = productOrderRepository.save(productOrder);
            Product product =productRepository.findByTensanpham(cart.getProduct().getTensanpham());
            product.setSoluong(product.getSoluong()-productOrder.getQuantity());
            productRepository.save(product);
            commonUtil.sendMailProductOrder(productOrder1,"Thành Công");
        }

    }

    @Override
    public List<ProductOrder> getOrderByUserId(Integer userId) {
        List<ProductOrder> orders = productOrderRepository.findByUserDtls_UserId(userId);
        return orders;
    }

    @Override
    public ProductOrder updateOrderStatus(Integer id, String status) {
        Optional<ProductOrder> findById = productOrderRepository.findById(id);
        if (findById.isPresent()) {
            ProductOrder productOrder = findById.get();
            productOrder.setStatus(status);
            ProductOrder updateOrder = productOrderRepository.save(productOrder);
            return updateOrder;
        }
        return null;
    }

    @Override
    public List<ProductOrder> getAllOrders() {
        List<ProductOrder> orders = productOrderRepository.findAll();
        return orders;
    }

    @Override
    public ProductOrder getOrderById(String orderId) {

        return  productOrderRepository.findByOrderId(orderId);
    }

    @Override
    public Page<ProductOrder> getAllOrdersPagination(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        return productOrderRepository.findAll(pageable);
    }
}
