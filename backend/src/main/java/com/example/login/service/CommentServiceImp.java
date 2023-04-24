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
        Comment comment = this.mapper.map(commentDto, Comment.class);
        comment.setPost(post);
        commentRepo.save(comment);

        return mapper.map(comment, CommentDto.class);
    }


    public List<CommentDto> findAllComment() {
        return commentRepo.findAll()
                .stream()
                .map((comment) -> this.mapper.map(comment, CommentDto.class))
                .collect(Collectors.toList());
    }

    public CommentDto editComment(String name, int commentId, String content, String title) {

    }


    public void deleteComment(Integer commentId) {
        commentRepo.deleteByCommentId(commentId)
                .orElseThrow(() -> new ResourceNotFoundException(Comment.class, "commentId || comment-people", commentId));
    }

    @Override
    public List<CommentDto> findCommentByPost(Integer postId) {
        var post=postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException(Post.class, "postId", postId));

        return commentRepo.findByPost(post)
                .stream()
                .map((comment) -> this.mapper.map(comment, CommentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto findCommentByKeyword(String keyword) {
        return null;
    }


}
