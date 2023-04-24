package com.example.login.service;


import com.example.login.dto.blog.PostDto;
import com.example.login.exception.ApiResponse;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * <文章服務></文章服務>
 */
public interface PostService {

    PostDto addPost(PostDto request,Integer categoryId, Integer userId);

    void deletePost(int postId);

    String editPost(String name, int postId, String content, String title);


    /**
     * pagination
     */
    List<PostDto> findAllPost(Integer pageNumber, Integer pageSize);

    List<PostDto> findPostByCategory(int categoryId);

    List<PostDto> findPostByUser(int userId);

    List<PostDto> findPostByKeyword(String keyword);
}
