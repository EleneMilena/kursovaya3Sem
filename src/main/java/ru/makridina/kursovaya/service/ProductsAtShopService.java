package ru.makridina.kursovaya.service;

import org.springframework.stereotype.Service;

@Service
public interface ProductsAtShopService {
    Integer CalcAtShopProductsById(String email);
}
