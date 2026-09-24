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

import org.springframework.web.bind.annotation.PathVariable;
import java.util.Optional;

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

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id,
                       @RequestParam(defaultValue = "0") int page,
                       Model model,
                       RedirectAttributes ra) {
        Optional<Category> opt = categoryService.findById(id);
        if (opt.isEmpty()) {
            ra.addFlashAttribute("message", "Không tìm thấy danh mục yêu cầu!");
            ra.addFlashAttribute("messageType", "danger");
            return "redirect:/admin/categories?page=" + page;
        }
        model.addAttribute("category", opt.get());
        model.addAttribute("categoryPage", categoryService.findAll(page, 10));
        model.addAttribute("pageTitle", "Cập Nhật Danh Mục #" + id);
        return "admin/categories";
    }

    @PostMapping
    public String saveOrUpdate(@Valid @ModelAttribute("category") Category category,
                               BindingResult result,
                               @RequestParam(defaultValue = "0") int page,
                               Model model,
                               RedirectAttributes ra) {
        boolean isDuplicate = (category.getId() == null)
                ? categoryService.existsBySlug(category.getSlug())
                : categoryService.existsBySlugAndIdNot(category.getSlug(), category.getId());

        if (category.getSlug() != null && !category.getSlug().isBlank() && isDuplicate) {
            result.rejectValue("slug", "duplicate", "Đường dẫn slug này đã tồn tại");
        }

        if (result.hasErrors()) {
            model.addAttribute("categoryPage", categoryService.findAll(page, 10));
            model.addAttribute("pageTitle", category.getId() == null ? "Quản Lý Danh Mục Hoa" : "Cập Nhật Danh Mục");
            return "admin/categories";
        }

        boolean isNew = (category.getId() == null);
        categoryService.save(category);
        ra.addFlashAttribute("message", isNew ? "Thêm danh mục mới thành công!" : "Cập nhật danh mục thành công!");
        ra.addFlashAttribute("messageType", "success");
        return "redirect:/admin/categories?page=" + page;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id,
                         @RequestParam(defaultValue = "0") int page,
                         RedirectAttributes ra) {
        Optional<Category> opt = categoryService.findById(id);
        if (opt.isEmpty()) {
            ra.addFlashAttribute("message", "Danh mục không tồn tại!");
            ra.addFlashAttribute("messageType", "danger");
            return "redirect:/admin/categories?page=" + page;
        }

        Category category = opt.get();
        if (category.getProducts() != null && !category.getProducts().isEmpty()) {
            ra.addFlashAttribute("message", "Không thể xóa danh mục đang có sản phẩm liên kết!");
            ra.addFlashAttribute("messageType", "danger");
            return "redirect:/admin/categories?page=" + page;
        }

        categoryService.deleteById(id);
        ra.addFlashAttribute("message", "Xóa danh mục thành công!");
        ra.addFlashAttribute("messageType", "success");
        return "redirect:/admin/categories?page=" + page;
    }
}
