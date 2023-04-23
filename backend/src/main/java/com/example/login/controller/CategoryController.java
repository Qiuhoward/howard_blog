package com.example.login.controller;

import com.example.login.dto.blog.CategoryDto;
import com.example.login.exception.ApiResponse;
import com.example.login.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <文章分類相關API></文章分類相關API>
 */
@RestController
@Tag(name = "文章分類(Category)")
@RequestMapping(value = "category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/add")
    @Operation(summary = "新增分類")
    public ResponseEntity<String> addCategory(@RequestBody CategoryDto categoryDto) {
        return new ResponseEntity<>(categoryService.addCategory(categoryDto), HttpStatus.CREATED);
    }

    @GetMapping("/")
    @Operation(summary = "尋找所有分類")
    public ResponseEntity<List<CategoryDto>> findAllCategory() {
        return ResponseEntity.ok().body(categoryService.findAllCategory());
    }

    @GetMapping("/{keyword}")
    @Operation(summary = "尋找關鍵字分類")
    public ResponseEntity<CategoryDto> findCategoryByKeyword(@PathVariable String keyword) {
        return ResponseEntity.ok().body(categoryService.findCategoryByKeyword(keyword));
    }

    @PutMapping("/{categoryId}")
    @Operation(summary = "編輯分類")
    public ResponseEntity<String> editCategory(@PathVariable Integer categoryId,@RequestParam(value = "title")String title) {

        return new ResponseEntity<>(categoryService.editCategory(categoryId, title), HttpStatus.CREATED);
    }

    @DeleteMapping("/{deleteId}")
    @Operation(summary = "刪除分類")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer deleteId) {
        categoryService.deleteCategory(deleteId);
        return new ResponseEntity<>(new ApiResponse("category is deleted", true), HttpStatus.OK);
    }


}
