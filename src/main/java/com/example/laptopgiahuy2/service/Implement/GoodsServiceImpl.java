package com.example.laptopgiahuy2.service.Implement;

import com.example.laptopgiahuy2.model.Goods;
import com.example.laptopgiahuy2.model.Product;
import com.example.laptopgiahuy2.repository.GoodsRepository;
import com.example.laptopgiahuy2.repository.ProductRepository;
import com.example.laptopgiahuy2.service.GoodsService;
import org.springframework.stereotype.Service;

@Service
public class GoodsServiceImpl implements GoodsService {
    private GoodsRepository goodsRepository;
    private ProductRepository productRepository;

    public GoodsServiceImpl(GoodsRepository goodsRepository, ProductRepository productRepository) {
        this.goodsRepository = goodsRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Goods saveGoods(Goods goods) {
        Product product = productRepository.findByTensanpham(goods.getProductName());
        product.setSoluong(product.getSoluong() + goods.getSoluong());
        product.setDanhMuc(goods.getCategoryName());
        return goodsRepository.save(goods);

    }
}
