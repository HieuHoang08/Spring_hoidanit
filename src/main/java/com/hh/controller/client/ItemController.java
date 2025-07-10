package com.hh.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hh.domain.Product;
import com.hh.service.ProductService;

@Controller
public class ItemController {


    private final ProductService productService;

    public ItemController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/product/{id}")
    public String getProductDetailPage(@PathVariable long id, Model model) {
        Product product = productService.getProductById(id).get();
        model.addAttribute("product", product);
        model.addAttribute("productId", id);
        return "client/product/detail";
    }
}
