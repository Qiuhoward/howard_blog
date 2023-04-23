package com.example.login.service;


import com.example.login.dao.post.Post;
import com.example.login.dto.blog.PostDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface PostService {

    Boolean addPost(PostDto request);

    void deletePost(int postId);

    String edit(String name, int postId, String content, String title);

    List<PostDto> findAllPost();

    List<PostDto> findPostByCategory(int categoryId);


    List<PostDto> findPostByUser(Integer userId);

    List<PostDto> findPostByKeyword(String keyword);
}
