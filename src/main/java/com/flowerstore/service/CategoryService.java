package com.flowerstore.service;

import com.flowerstore.entity.Category;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Page<Category> findAll(int page, int size);
    List<Category> findAll();
    Optional<Category> findById(Integer id);
    Category save(Category category);
    void deleteById(Integer id);
    boolean existsBySlug(String slug);
    boolean existsBySlugAndIdNot(String slug, Integer id);
}
