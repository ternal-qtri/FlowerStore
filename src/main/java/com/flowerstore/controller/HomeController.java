package com.flowerstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @GetMapping({"/", "/index", "/home"})
    public String index(Model model) {
        return "client/index";
    }

    @GetMapping("/products")
    public String products(@RequestParam(value = "category", required = false) String category,
                           @RequestParam(value = "q", required = false) String keyword,
                           Model model) {
        model.addAttribute("selectedCategory", category);
        model.addAttribute("keyword", keyword);
        return "client/products";
    }

    @GetMapping("/product-detail")
    public String productDetailDefault(Model model) {
        return "client/product-detail";
    }

    @GetMapping("/products/{id}")
    public String productDetail(@PathVariable("id") String id, Model model) {
        model.addAttribute("productId", id);
        return "client/product-detail";
    }

    @GetMapping("/cart")
    public String cart(Model model) {
        return "client/cart";
    }

    @GetMapping("/checkout")
    public String checkout(Model model) {
        return "client/checkout";
    }

    @GetMapping("/order-success")
    public String orderSuccess(Model model) {
        return "client/order-success";
    }

    @GetMapping("/my-orders")
    public String myOrders(Model model) {
        return "client/my-orders";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        return "client/profile";
    }
}
