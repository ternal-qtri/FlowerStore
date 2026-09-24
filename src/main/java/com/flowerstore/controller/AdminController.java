package com.flowerstore.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @ModelAttribute
    public void addAttributes(HttpServletRequest request, Model model) {
        model.addAttribute("currentUri", request.getRequestURI());
    }

    @GetMapping({"", "/", "/dashboard"})
    public String dashboard(Model model) {
        model.addAttribute("pageTitle", "Tổng Quan Hệ Thống");
        return "admin/dashboard";
    }

    @GetMapping("/revenue")
    public String revenue(Model model) {
        model.addAttribute("pageTitle", "Báo Cáo Doanh Thu");
        return "admin/revenue";
    }


    @GetMapping("/orders")
    public String orders(Model model) {
        model.addAttribute("pageTitle", "Quản Lý Đơn Hàng");
        return "admin/order-list";
    }

    @GetMapping("/accounts")
    public String accounts(Model model) {
        model.addAttribute("pageTitle", "Quản Lý Tài Khoản");
        return "admin/account-list";
    }

    @GetMapping("/accounts/form")
    public String accountForm(Model model) {
        model.addAttribute("pageTitle", "Cập Nhật Tài Khoản");
        return "admin/account-form";
    }

    @GetMapping("/vip-customers")
    public String vipCustomers(Model model) {
        model.addAttribute("pageTitle", "Khách Hàng Thân Thiết (VIP)");
        return "admin/vip-customers";
    }
}
