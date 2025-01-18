package com.example.laptopgiahuy2.service;

import com.example.laptopgiahuy2.model.Goods;

import java.util.List;

public interface GoodsService {
    public Goods saveGoods(Goods goods);
    List<Goods> getAllGoods();
}
