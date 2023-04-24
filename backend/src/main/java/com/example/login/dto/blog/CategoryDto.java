package com.example.login.dto.blog;

import lombok.*;


/**
 * <文章分類封包></文章分類封包>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto  {
    private Integer categoryId;
    private String title;
    private String description;
}