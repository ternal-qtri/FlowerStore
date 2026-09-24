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

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public String list(@RequestParam(defaultValue = "0") int page, Model model) {
        model.addAttribute("categoryPage", categoryService.findAll(page, 10));
        if (!model.containsAttribute("category")) {
            model.addAttribute("category", new Category());
        }
        model.addAttribute("pageTitle", "Quản Lý Danh Mục Hoa");
        return "admin/categories";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("category") Category category,
                         BindingResult result,
                         @RequestParam(defaultValue = "0") int page,
                         Model model,
                         RedirectAttributes ra) {
        if (category.getSlug() != null && !category.getSlug().isBlank() && categoryService.existsBySlug(category.getSlug())) {
            result.rejectValue("slug", "duplicate", "Đường dẫn slug này đã tồn tại");
        }

        if (result.hasErrors()) {
            model.addAttribute("categoryPage", categoryService.findAll(page, 10));
            model.addAttribute("pageTitle", "Quản Lý Danh Mục Hoa");
            return "admin/categories";
        }

        categoryService.save(category);
        ra.addFlashAttribute("message", "Thêm danh mục mới thành công!");
        ra.addFlashAttribute("messageType", "success");
        return "redirect:/admin/categories";
    }
}
