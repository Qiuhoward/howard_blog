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

/**
 * <文章留言相關API></文章留言相關API>
 */
@RestController
@Tag(name = "文章留言(Comment)")
@RequestMapping(value = "comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/post/{postId}/comment")
    @Operation(summary = "新增留言")
    public ResponseEntity<CommentDto> addComment(@RequestBody CommentDto commentDto, @PathVariable String postId) {
        return new ResponseEntity<>(commentService.addComment(commentDto, Integer.parseInt(postId)), HttpStatus.CREATED);
    }

    @GetMapping("/")
    @Operation(summary = "尋找所有留言")
    public ResponseEntity<List<CommentDto>> findAllComment() {
        return ResponseEntity.ok().body(commentService.findAllComment());
    }

    @GetMapping("/keyword")
    @Operation(summary = "尋找關鍵字留言")
    public ResponseEntity<List<CommentDto>> findCommentByKeyword(@RequestParam(value = "keyword") String keyword) {
        return ResponseEntity.ok().body(commentService.findCommentByKeyword(keyword));
    }

    @PutMapping("/{commentId}")
    @Operation(summary = "編輯留言")
    public ResponseEntity<CommentDto> editComment(
            @RequestParam(value = "content") String content,
            @PathVariable String commentId) {

        return new ResponseEntity<>(commentService.editComment(Integer.parseInt(commentId), content), HttpStatus.OK);
    }

    @DeleteMapping("/{deleteId}")
    @Operation(summary = "刪除留言")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable String deleteId) {
        commentService.deleteComment(Integer.parseInt(deleteId));
        return new ResponseEntity<>(new ApiResponse("comment" + deleteId + " is deleted", true), HttpStatus.OK);
    }


    @GetMapping("post/{postId}/comments")
    @Operation(summary = "尋找某篇文章所有留言")
    public ResponseEntity<List<CommentDto>> findCommentsByPost(@PathVariable String postId) {
        return ResponseEntity.ok().body(commentService.findCommentByPost(Integer.parseInt(postId)));
    }


}
