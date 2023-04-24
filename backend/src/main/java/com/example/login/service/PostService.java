package com.example.login.service;


import com.example.login.dto.blog.PostDto;

import java.util.List;

/**
 * <文章服務></文章服務>
 */
public interface PostService {

    PostDto addPost(PostDto request,Integer categoryId, Integer userId);

    void deletePost(int postId);

    PostDto editPost(String name, Integer postId, String content, String title);


    /**
     * pagination
     */
    List<PostDto> findAllPost(Integer pageNumber, Integer pageSize);

    List<PostDto> findPostByCategory(int categoryId);

    List<PostDto> findPostByUser(int userId);

    List<PostDto> findPostByKeyword(String keyword);
}
