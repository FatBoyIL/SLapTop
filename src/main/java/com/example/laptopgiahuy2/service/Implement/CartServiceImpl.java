package com.example.laptopgiahuy2.service.Implement;

import com.example.laptopgiahuy2.model.Cart;
import com.example.laptopgiahuy2.model.Product;
import com.example.laptopgiahuy2.model.UserDtls;
import com.example.laptopgiahuy2.repository.CartRepository;
import com.example.laptopgiahuy2.repository.ProductRepository;
import com.example.laptopgiahuy2.repository.UserDtlsRepository;
import com.example.laptopgiahuy2.service.CartService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
@Service
public class CartServiceImpl implements CartService {
    private CartRepository cartRepository;
    private UserDtlsRepository userDtlsRepository;
    private ProductRepository productRepository;

    public CartServiceImpl(CartRepository cartRepository, UserDtlsRepository userDtlsRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.userDtlsRepository = userDtlsRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Cart saveCart(Integer productId, Integer userId) {
        UserDtls userDtls= userDtlsRepository.findById(userId).get();
        Product product=productRepository.findById(productId).get();
        Cart cartStatus= cartRepository.findByUser_UserIdAndProduct_ProductId(productId, userId);
        Cart cart = new Cart();
        if (ObjectUtils.isEmpty(cartStatus)) {

            cart.setProduct(product);
            cart.setUser(userDtls);
            cart.setQuantity(1);
            cart.setTotalPrice(product.getGiaSale());
        }
        else {
            cart = cartStatus;
            cart.setQuantity(cart.getQuantity() + 1);
            cart.setTotalPrice(cart.getQuantity() * product.getGiaSale());

        }
        Cart savedCart=cartRepository.save(cart);

        return savedCart;
    }

    @Override
    public List<Cart> getCartByUserId(Integer userId) {
        return List.of();
    }
}
