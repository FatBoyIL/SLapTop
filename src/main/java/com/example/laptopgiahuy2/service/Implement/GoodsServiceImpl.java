package com.example.laptopgiahuy2.service.Implement;

import com.example.laptopgiahuy2.model.Goods;
import com.example.laptopgiahuy2.model.Product;
import com.example.laptopgiahuy2.repository.GoodsRepository;
import com.example.laptopgiahuy2.repository.ProductRepository;
import com.example.laptopgiahuy2.service.GoodsService;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

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
        int soLuong=product.getSoluong() + goods.getSoluong();
        product.setSoluong(soLuong);
        product.setDanhMuc(goods.getCategoryName());
        goods.setTongtien(BigInteger.valueOf(goods.getSoluong()*product.getGia()));
        return goodsRepository.save(goods);

    }

    @Override
    public List<Goods> getAllGoods() {
        return goodsRepository.findAll();
    }
}
