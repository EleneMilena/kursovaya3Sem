package ru.makridina.kursovaya.controller;


import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.makridina.kursovaya.repository.ProductsRepository;
import ru.makridina.kursovaya.repository.UserActionsRepository;
import ru.makridina.kursovaya.entity.Product;
import ru.makridina.kursovaya.service.GetRoleService;
import ru.makridina.kursovaya.service.GetRoleServiceImp;
import ru.makridina.kursovaya.service.GetUsernameService;
import ru.makridina.kursovaya.service.UserActionService;
import ru.makridina.kursovaya.service.*;

import java.util.List;
import java.util.Optional;
@Log4j2
@Slf4j
@Controller
public class ProductsController {
    @Autowired
    private ProductsRepository productsRepository;
    private GetRoleService getRoleServiceImp;
    private GetUsernameService getUsernameService;
    private UserActionService userActionService;
    public ProductsController(GetRoleServiceImp getRoleServiceImp,
                          GetUsernameService getUsernameService,
                          UserActionService userActionService)
    {
        this.getRoleServiceImp = getRoleServiceImp;
        this.getUsernameService = getUsernameService;
        this.userActionService = userActionService;
    }


    @Autowired
    private UserActionsRepository userActionsRepository;
    @GetMapping("/list")
    public ModelAndView getAllProduct() {
        ModelAndView mav = new ModelAndView("list-products");
        if (getRoleServiceImp.getRoleCurrentUser().equals("ROLE_ADMIN"))
        {
            mav.addObject("products", productsRepository.findAll());
        }
        else
        {
            mav.addObject("products", productsRepository.findByEmail(getUsernameService.getusername()));
        }
        mav.addObject("mainUserRole", getRoleServiceImp.getRoleCurrentUser());
        log.info("/list -> connection");
        userActionService.setUserAction("Получен список продуктов");
        return mav;
    }
    @GetMapping("/userActions")
    public ModelAndView getAllLogs() {
        log.info("/list -> connection");
        ModelAndView mav = new ModelAndView("list-actions");
        mav.addObject("userActions", userActionsRepository.findAll());
        userActionService.setUserAction("Получен список действий");
        return mav;
    }
    @GetMapping("/CalcProductNumber")
    public ModelAndView calcProductNumber(ModelAndView modelAndView) {
        log.info("/list -> connection");
        ModelAndView mav = new ModelAndView("list-products");
        List<Product> productList = null;
        if (getRoleServiceImp.getRoleCurrentUser().equals("ROLE_ADMIN"))
        {
            productList = productsRepository.findAll();
        }
        else
        {
            productList = productsRepository.findByEmail(getUsernameService.getusername());

        }
        mav.addObject("mainUserRole", getRoleServiceImp.getRoleCurrentUser());
        mav.addObject("products", productList);
        mav.addObject("productNumber", Integer.toString(productList.size()));
        userActionService.setUserAction("Количество продуктов");
        return mav;
    }

    @GetMapping("/addProductForm")
    public ModelAndView addStudentForm()
    {
        ModelAndView mav = new ModelAndView("add-product-form");
        Product product = new Product();
        mav.addObject("product", product);
        userActionService.setUserAction("Добавление продукта пользователем");
        return mav;
    }
    @PostMapping("/saveProduct")
    public String saveProduct(@ModelAttribute Product product)
    {
        product.setEmail(getUsernameService.getusername());
        productsRepository.save(product);
        userActionService.setUserAction("Сохранение продукта");
        return "redirect:/list";
    }
    @GetMapping("/showUpdateForm")
    public ModelAndView showUpdateForm(@RequestParam Long productId)
    {
        ModelAndView mav = new ModelAndView("add-product-form");
        Optional<Product> optionalProduct = productsRepository.findById(productId);
        Product product = new Product();
        if(optionalProduct.isPresent())
        {
            product = optionalProduct.get();
        }
        mav.addObject("product", product);
        userActionService.setUserAction("Редактирование продукта");
        return mav;
    }
    @GetMapping("/deleteProduct")
    public String deleteStudent(@RequestParam Long productId)
    {
        productsRepository.deleteById(productId);
        userActionService.setUserAction("Удаление продукта");
        return "redirect:/list";
    }

}
