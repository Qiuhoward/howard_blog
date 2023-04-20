package com.example.login.service;

import com.example.login.dao.post.Comment;
import com.example.login.dao.post.CommentRepo;
import com.example.login.dto.blog.CommentDto;
import com.example.login.exception.EventError;
import com.example.login.exception.InternalServerException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService implements BasicService{

    private final CommentRepo commentRepo;

    public CommentService(CommentRepo repo) {
        this.commentRepo = repo;
    }

    public String addComment(CommentDto commentDto) {
        int postId = commentDto.getPostId();
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


    public List<Comment> findAll(){
       return  commentRepo.findAll();
    }

    public void delete(int commentId, String name) throws InternalServerException {
        commentRepo.deleteCommentByCommentIdAndCommentPeople(commentId, name)
                .orElseThrow(()->new InternalServerException(EventError.DATA_IS_NOT_EXIST.toString()));
    }

}
