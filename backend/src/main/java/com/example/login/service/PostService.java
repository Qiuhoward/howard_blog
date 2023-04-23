package com.example.login.service;


import com.example.login.dto.blog.PostDto;

import java.util.List;


public interface PostService {

    String addPost(PostDto request);

    void deletePost(int postId);

    String editPost(String name, int postId, String content, String title);

    List<PostDto> findAllPost(Integer pageNumber,Integer pageSize);

    List<PostDto> findPostByCategory(int categoryId);

    List<PostDto> findPostByUser(Integer userId);

    List<PostDto> findPostByKeyword(String keyword);
}
