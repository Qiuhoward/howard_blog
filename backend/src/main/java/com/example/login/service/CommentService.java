package com.example.login.service;

import com.example.login.dao.post.Comment;
import com.example.login.dao.post.CommentRepo;
import com.example.login.dto.blog.CommentDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

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
    public String deleteComment(int commentId,String name){
        commentRepo.deleteCommentByCommentIdAndCommentPeople(commentId,name);
        return "刪除成功";
    }
    //尋找所有關於那篇文章得留言
}
