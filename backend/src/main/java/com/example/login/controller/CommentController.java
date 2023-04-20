package com.example.login.controller;

import com.example.login.dto.blog.CommentDto;
import com.example.login.service.CommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
