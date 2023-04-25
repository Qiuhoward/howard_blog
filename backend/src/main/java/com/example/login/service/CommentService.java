package com.example.login.service;

import com.example.login.dto.blog.CommentDto;

import java.util.List;

/**
 * <文章留言服務></文章留言服務>
 */
public interface CommentService {

    CommentDto addComment(CommentDto commentDto,Integer postId);

    List<CommentDto> findAllComment();

    CommentDto editComment(Integer commentId, String content);

    void deleteComment(Integer commentId);

    List<CommentDto> findCommentByPost(Integer postId);

    List<CommentDto> findCommentByKeyword(String keyword);
}
