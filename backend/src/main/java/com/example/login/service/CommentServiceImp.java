package com.example.login.service;

import com.example.login.dao.post.Comment;
import com.example.login.dao.post.CommentRepo;
import com.example.login.dao.post.Post;
import com.example.login.dao.post.PostRepo;
import com.example.login.dto.blog.CommentDto;
import com.example.login.exception.ResourceIsExistException;
import com.example.login.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImp implements CommentService {

    private final CommentRepo commentRepo;
    private final PostRepo postRepo;
    private final ModelMapper mapper;

    public CommentServiceImp(CommentRepo repo, PostRepo postRepo, ModelMapper mapper) {
        this.commentRepo = repo;
        this.postRepo = postRepo;
        this.mapper = mapper;
    }

    public CommentDto addComment(CommentDto commentDto, Integer postId) {
        var post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceIsExistException(Post.class, "postId", postId));
        var comment = this.mapper.map(commentDto, Comment.class);
        comment.setPost(post);
        comment.setCreateAt(new Date());
        commentRepo.save(comment);
        System.out.println(comment.getContent());
        System.out.println(commentDto.getContent());
        return mapper.map(comment,CommentDto.class);
    }


    public List<CommentDto> findAllComment() {
        return commentRepo.findAll()
                .stream()
                .map((comment) -> this.mapper.map(comment, CommentDto.class))
                .collect(Collectors.toList());
    }

    public CommentDto editComment(Integer commentId, String content) {
        var comment = commentRepo.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException(Comment.class, "commentId", commentId));
        comment.setContent(content);
        comment=commentRepo.save(comment);
        return this.mapper.map(comment, CommentDto.class);
    }


    public void deleteComment(Integer commentId) {
        var comment=commentRepo.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException(Comment.class, "commentId", commentId));
        commentRepo.delete(comment);
    }

    @Override
    public List<CommentDto> findCommentByPost(Integer postId) {
        var post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException(Post.class, "postId", postId));

        return commentRepo.findCommentByPost(post)
                .stream()
                .map((comment) -> this.mapper.map(comment, CommentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto findCommentByKeyword(String keyword) {
        return null;
    }


}
