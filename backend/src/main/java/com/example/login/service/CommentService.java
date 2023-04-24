package com.example.login.service;

import com.example.login.dto.blog.CommentDto;

import java.util.List;

/**
 * <文章留言服務></文章留言服務>
 */
public interface CommentService {

    CommentDto addComment(CommentDto commentDto,Integer postId);

    List<CommentDto> findAllComment();

    CommentDto editComment(String name, int commentId, String content, String title);

    void deleteComment(Integer commentId);

    List<CommentDto> findCommentByPost(Integer postId);

    CommentDto findCommentByKeyword(String keyword);
}
