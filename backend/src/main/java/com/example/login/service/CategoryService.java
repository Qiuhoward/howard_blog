package com.example.login.service;

import com.example.login.dto.blog.CategoryDto;

import java.util.List;

/**
 * <文章分類服務></文章分類服務>
 */
public interface CategoryService {

    String addCategory(CategoryDto categoryDto);

    List<CategoryDto> findAllCategory();

    String editCategory(int categoryId, String title);

    void deleteCategory(Integer categoryId);

    CategoryDto findCategoryByKeyword(String keyword);
}
