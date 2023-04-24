package com.example.login.service;

import com.example.login.dao.post.Category;
import com.example.login.dao.post.CategoryRepo;
import com.example.login.dto.blog.CategoryDto;
import com.example.login.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImp implements CategoryService {

    private final CategoryRepo categoryRepo;
    private final ModelMapper mapper;

    public CategoryServiceImp(CategoryRepo categoryRepo, ModelMapper mapper) {
        this.categoryRepo = categoryRepo;
        this.mapper = mapper;
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        var category = this.mapper.map(categoryDto, Category.class);
        categoryRepo.save(category);
        return this.mapper.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> findAllCategory() {
        return categoryRepo.findAll()
                .stream()
                .map((category -> this.mapper.map(category, CategoryDto.class)))
                .collect(Collectors.toList());

    }

    @Override
    public CategoryDto editCategory(int categoryId, CategoryDto categoryDto) {
        var category = this.mapper.map(categoryDto, Category.class);
        categoryRepo.save(category);

        return this.mapper.map(category, CategoryDto.class);
    }


    @Override
    public void deleteCategory(Integer categoryId) {
        categoryRepo.findById(categoryId).orElseThrow(
                ()->new ResourceNotFoundException(Category.class,"categoryId",categoryId));
        categoryRepo.deleteById(categoryId);
    }

    @Override
    public CategoryDto findCategoryByKeyword(String keyword) {
        return null;
    }
}
