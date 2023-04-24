package com.example.login.service;

import com.example.login.dao.post.Category;
import com.example.login.dao.post.CategoryRepo;
import com.example.login.dto.blog.CategoryDto;
import com.example.login.exception.ResourceNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
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
        log.info("新增分類:category");
        var category = this.mapper.map(categoryDto, Category.class);
        category = categoryRepo.save(category);
        return this.mapper.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> findAllCategory() {
        log.info("尋找所有分類");
        return categoryRepo.findAll()
                .stream()
                .map((category -> this.mapper.map(category, CategoryDto.class)))
                .collect(Collectors.toList());

    }

    @Override
    public CategoryDto editCategory(int categoryId, CategoryDto categoryDto) {
        log.info("編輯指定分類 ");

        var category = categoryRepo.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException(Category.class, "categoryId", categoryId));
        category.setTitle(category.getTitle());
        category.setDescription(category.getDescription());
        categoryRepo.save(category);

        return this.mapper.map(category, CategoryDto.class);
    }


    @Override
    public void deleteCategory(Integer categoryId) {
        log.info("刪除指定分類");
        categoryRepo.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException(Category.class, "categoryId", categoryId));
        categoryRepo.deleteById(categoryId);
    }

    @Override
    public List<CategoryDto> findCategoryByTitle(String keyword) {
        log.info("關鍵字尋找分類");
        List<Category> categoryList = categoryRepo.findByTitleContaining(keyword);
        return categoryList
                .stream().map((category -> this.mapper.map(category, CategoryDto.class)))
                .toList();

    }
}
