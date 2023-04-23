package com.example.login.service;

import com.example.login.dao.post.Category;
import com.example.login.dao.post.CategoryRepo;
import com.example.login.dto.blog.CategoryDto;
import com.example.login.exception.ResourceIsExistException;
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
    public String addCategory(CategoryDto categoryDto) {
        String title = categoryDto.getTitle();
        categoryRepo.findByTitle(title).orElseThrow(
                () -> new ResourceIsExistException(Category.class, "title", title));

        var category = Category
                .builder()
                .title(title)
                .build();
        categoryRepo.save(category);
        return "新增成功";
    }

    @Override
    public List<CategoryDto> findAllCategory() {
        return categoryRepo.findAll()
                .stream()
                .map((category -> this.mapper.map(category, CategoryDto.class)))
                .collect(Collectors.toList());

    }

    @Override
    public String editCategory(int categoryId, String title) {
        var category = categoryRepo.findById(categoryId).orElseThrow(
                () -> new ResourceIsExistException(Category.class, "categoryId", categoryId));

        category.setTitle(title);
        categoryRepo.save(category);
        return "修改成功";
    }


    @Override
    public void deleteCategory(Integer categoryId) {
        categoryRepo.deleteById(categoryId);
    }
}
