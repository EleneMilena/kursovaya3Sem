package ru.makridina.kursovaya.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.makridina.kursovaya.repository.ProductsRepository;

@Service
public class ProductsAtShopServiceImp implements ProductsAtShopService{
    @Autowired
    ProductsRepository productsRepository;
    @Override
    public Integer CalcAtShopProductsById(String email)
    {
        return productsRepository.findByEmail(email).size();
    }
}
