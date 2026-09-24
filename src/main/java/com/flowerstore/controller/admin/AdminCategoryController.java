package com.flowerstore.controller.admin;

import com.flowerstore.entity.Category;
import com.flowerstore.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public String list(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<Category> categoryPage = categoryService.findAll(page, 10);
        model.addAttribute("categoryPage", categoryPage);
        model.addAttribute("category", new Category());
        model.addAttribute("pageTitle", "Quản Lý Danh Mục Hoa");
        return "admin/categories";
    }
}
