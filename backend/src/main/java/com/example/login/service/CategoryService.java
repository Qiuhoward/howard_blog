package com.example.login.service;

import com.example.login.dto.blog.CategoryDto;

import java.util.List;

/**
 * <文章分類服務></文章分類服務>
 */
public interface CategoryService {

    CategoryDto addCategory(CategoryDto categoryDto);

    List<CategoryDto> findAllCategory();

    CategoryDto editCategory(int categoryId, CategoryDto categoryDto);

    void deleteCategory(Integer categoryId);

    CategoryDto findCategoryByKeyword(String keyword);
}
