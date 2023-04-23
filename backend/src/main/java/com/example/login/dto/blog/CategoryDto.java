package com.example.login.dto.blog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CategoryDto extends BasicDto {
    private String title;
}