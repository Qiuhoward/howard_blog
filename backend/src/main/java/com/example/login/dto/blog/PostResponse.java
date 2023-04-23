package com.example.login.dto.blog;

import jakarta.persistence.GeneratedValue;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


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
