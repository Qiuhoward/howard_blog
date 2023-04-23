package com.example.login.service;

import com.example.login.dto.blog.CategoryDto;

import java.util.List;

public interface CategoryService {

    String addCategory(CategoryDto categoryDto);

    List<CategoryDto> findAllCategory();

    String editCategory(int categoryId, String title);

    void deleteCategory(Integer categoryId);

}
