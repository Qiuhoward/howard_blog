package com.example.login.service;

import com.example.login.dao.post.Comment;
import com.example.login.dao.post.CommentRepo;
import com.example.login.dao.post.Post;
import com.example.login.dao.post.PostRepo;
import com.example.login.dto.blog.CommentDto;
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

    public String addComment(CommentDto commentDto) {
        int postId = commentDto.getCommentId();
        String addComment = commentDto.getComment();
        String commentPeople = commentDto.getCommentPeople();
        var comment = Comment.builder()
                .postId(postId)
                .commentPeople(commentPeople)
                .content(addComment)
                .build();
        commentRepo.save(comment);

        return "新增成功";
    }


    public List<CommentDto> findAllComment() {
        return commentRepo.findAll()
                .stream()
                .map((comment) -> this.mapper.map(comment, CommentDto.class))
                .collect(Collectors.toList());
    }

    public String editComment(String name, int commentId, String content, String title) {
        var comment = commentRepo.findCommentByCommentIdAndCommentPeople(commentId, name).orElseThrow(
                () -> new ResourceNotFoundException(Comment.class, "commentId", commentId)
        );
        comment.setContent(content);
        comment.setCreateAt(new Date());
        commentRepo.save(comment);
        return "修改成功";
    }


    public void deleteComment(Integer commentId) {
        commentRepo.deleteByCommentId(commentId)
                .orElseThrow(() -> new ResourceNotFoundException(Comment.class, "commentId || comment-people", commentId));
    }

    @Override
    public List<CommentDto> findCommentByPost(Integer postId) {
        postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException(Post.class, "postId", postId));

        return commentRepo.findByPostId(postId)
                .stream()
                .map((comment) -> this.mapper.map(comment, CommentDto.class))
                .collect(Collectors.toList());
    }


}
