package com.example.login.controller;

import com.example.login.dao.post.Comment;
import com.example.login.dao.post.Post;
import com.example.login.dto.blog.CommentDto;
import com.example.login.exception.InternalServerException;
import com.example.login.service.CommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "文章留言")
@RequestMapping(value = "comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addPost(@RequestBody CommentDto commentDto) {
        return ResponseEntity.ok().body(commentService.addComment(commentDto));
    }
    @GetMapping("/find_all")
    public List<Comment> findAll() {

        return commentService.findAll();
    }

    @DeleteMapping("/delete")
    public void Post(int postId, String name) throws InternalServerException {
        commentService.delete(postId, name);
    }


}
