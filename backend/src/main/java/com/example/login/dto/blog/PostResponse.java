package com.example.login.dto.blog;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * <分頁 排序></分頁 排序>
 */
@Getter
@Setter
public class PostResponse {

    private List<PostDto> content;
    private int pageNumber;
    private int pageSize;
    private int totalElement;
    private int totalPage;
    private Boolean lastPage;
}
