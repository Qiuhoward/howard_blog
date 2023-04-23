package com.example.login.controller;

import com.example.login.dto.blog.PostDto;
import com.example.login.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <文章窗口></文章窗口>
 */

@RestController
@Tag(name = "文章(Post)")

@RequestMapping(value = "post")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/add")
    @Operation(summary = "新增文章")
    public ResponseEntity<String> addPost(@RequestBody PostDto postDto) {
        return ResponseEntity.ok().body(postService.addPost(postDto));
    }

    @PutMapping("/{postId}")
    @Operation(summary = "編輯文章")
    public ResponseEntity<String> editPost(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "content") String content,
            @RequestParam(value = "title") String title,
            @PathVariable Integer postId) {

        return new ResponseEntity<>(postService.editPost(name, postId, content, title), HttpStatus.CREATED);
    }

    @GetMapping("/")
    @Operation(summary = "搜尋所有文章")
    public ResponseEntity<List<PostDto>> findAllPost(
            @RequestParam(value = "pageSize", defaultValue = "1", required = false) Integer pageSize,
            @RequestParam(value = "pageNumber", defaultValue = "5", required = false) Integer pageNumber
    ) {

        return ResponseEntity.ok().body(postService.findAllPost(pageNumber, pageSize));
    }

    @DeleteMapping("/{postId}")
    @Operation(summary = "刪除文章")
    public void deletePost(@PathVariable Integer postId) {
        postService.deletePost(postId);
    }

    @GetMapping("category/{categoryId}/posts")
    @Operation(summary = "搜尋特定類別文章")
    public ResponseEntity<List<PostDto>> findPostByCategory(@PathVariable Integer categoryId) {
        return ResponseEntity.ok().body(postService.findPostByCategory(categoryId));
    }

    @GetMapping("user/{userId}/posts")
    @Operation(summary = "搜尋特定使用者文章")
    public ResponseEntity<List<PostDto>> findPostByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok().body(postService.findPostByUser(userId));
    }

    @GetMapping("/keyword")
    @Operation(summary = "搜尋關鍵字文章")
    public ResponseEntity<List<PostDto>> findPostByKeyword(@RequestParam(value = "keyword") String keyword) {
        return ResponseEntity.ok().body(postService.findPostByKeyword(keyword));
    }
}
