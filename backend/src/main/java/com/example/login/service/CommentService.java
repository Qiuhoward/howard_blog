package com.example.login.service;

import com.example.login.dto.blog.CommentDto;

import java.util.List;

public interface CommentService {

    String addComment(CommentDto commentDto);

    List<CommentDto> findAllComment();

    String editComment(String name, int commentId, String content, String title);

    void deleteComment(Integer commentId);

    List<CommentDto> findCommentByPost(Integer postId);
}
