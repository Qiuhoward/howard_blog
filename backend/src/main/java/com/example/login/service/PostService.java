package com.example.login.service;


import com.example.login.dao.post.Post;
import com.example.login.dto.blog.PostDto;
import com.example.login.dto.blog.PostResponse;

import java.util.List;

/**
 * <文章服務></文章服務>
 */
public interface PostService {

    PostDto addPost(PostDto request,Integer categoryId, Integer userId);

    void deletePost(Integer postId);

    PostDto editPost( Integer postId, String content, String title);

    /**
     * pagination && sort
     */
    PostResponse findAllPost(Integer pageNumber, Integer pageSize,String sortBy,String sortDic);

    List<PostDto> findPostByCategory(Integer categoryId);

    List<PostDto> findPostByUser(int userId);

    List<PostDto> findPostByTitle(String keyword);

    List<PostDto> findPostByUserAndDesc(Integer userId);
}
