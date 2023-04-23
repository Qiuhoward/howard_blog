package com.example.login.controller;

import com.example.login.dto.blog.CommentDto;
import com.example.login.exception.ApiResponse;
import com.example.login.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "文章留言(Comment)")
@RequestMapping(value = "comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/add")
    @Operation(summary = "新增留言")
    public ResponseEntity<String> addComment(@RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.addComment(commentDto), HttpStatus.CREATED);
    }

    @GetMapping("/")
    @Operation(summary = "尋找所有留言")
    public ResponseEntity<List<CommentDto>> findAllComment() {
        return ResponseEntity.ok().body(commentService.findAllComment());
    }

    @PutMapping("/edit")
    @Operation(summary = "編輯留言")
    public ResponseEntity<String>editPost(String name, int postId, String content, String title) {

        return new ResponseEntity<>(commentService.editComment(name, postId, content, title), HttpStatus.CREATED);
    }

    @DeleteMapping("/{deleteId}")
    @Operation(summary = "刪除留言")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer deleteId) {
        commentService.deleteComment(deleteId);
        return new ResponseEntity<>(new ApiResponse("comment is deleted", true), HttpStatus.OK);
    }


    @GetMapping("post/{postId}/comments")
    @Operation(summary = "刪除某篇文章留言")
    public ResponseEntity<List<CommentDto>> findCommentsByPost(@PathVariable Integer postId) {
        return ResponseEntity.ok().body(commentService.findCommentByPost(postId));
    }

}
