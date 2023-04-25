package com.example.login.dto.blog;


import java.util.List;

/**
 * <分頁 排序></分頁 排序>
 */

public record PostResponse(List<PostDto> content,
                           int pageNumber,
                           int pageSize,
                           int totalElement,
                           int totalPage,
                           Boolean lastPage) {
}
