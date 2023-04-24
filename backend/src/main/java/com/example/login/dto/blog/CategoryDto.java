package com.example.login.dto.blog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


/**
 * <文章分類封包></文章分類封包>
 */
@Getter
@Builder
@AllArgsConstructor
public class CategoryDto extends BasicDto {
    private Integer categoryId;
    private String title;
    private String description;
}