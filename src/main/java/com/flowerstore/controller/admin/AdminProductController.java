package com.flowerstore.controller.admin;

import com.flowerstore.entity.Product;
import com.flowerstore.service.CategoryService;
import com.flowerstore.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class AdminProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping
    public String list(@RequestParam(required = false) String keyword,
                       @RequestParam(required = false) Integer categoryId,
                       @RequestParam(defaultValue = "0") int page,
                       Model model) {
        Page<Product> productPage = productService.search(keyword, categoryId, page, 10);
        model.addAttribute("productPage", productPage);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("keyword", keyword);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("pageTitle", "Quản Lý Mẫu Hoa Tươi");
        return "admin/product-list";
    }

    @GetMapping("/form")
    public String createForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("pageTitle", "Thêm Mẫu Hoa Mới");
        return "admin/product-form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("product") Product product,
                       BindingResult result,
                       Model model,
                       RedirectAttributes ra) {
        boolean isDuplicate = (product.getId() == null)
                ? productService.existsBySlug(product.getSlug())
                : productService.existsBySlugAndIdNot(product.getSlug(), product.getId());

        if (product.getSlug() != null && !product.getSlug().isBlank() && isDuplicate) {
            result.rejectValue("slug", "duplicate", "Đường dẫn slug này đã tồn tại");
        }

        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("pageTitle", product.getId() == null ? "Thêm Mẫu Hoa Mới" : "Cập Nhật Thông Tin Hoa");
            return "admin/product-form";
        }

        boolean isNew = (product.getId() == null);
        productService.save(product);
        ra.addFlashAttribute("message", isNew ? "Thêm mẫu hoa mới thành công!" : "Cập nhật mẫu hoa thành công!");
        ra.addFlashAttribute("messageType", "success");
        return "redirect:/admin/products";
    }
}
