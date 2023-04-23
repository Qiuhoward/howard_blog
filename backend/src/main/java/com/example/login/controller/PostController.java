package com.example.login.controller;

import com.example.login.dto.blog.PostDto;
import com.example.login.service.PostService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <文章窗口></文章窗口>
 */

@RestController
@Tag(name = "文章")
@RequestMapping(value = "post")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/add")
    public ResponseEntity<Boolean> addPost(@RequestBody PostDto postDto) {
        return ResponseEntity.ok().body(postService.addPost(postDto));
    }

    @PutMapping("/edit")
    public ResponseEntity<String> editPost(String name, int postId, String content, String title) {

        return new ResponseEntity<>(postService.edit(name, postId, content, title), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<PostDto>> findAllPost() {

        return ResponseEntity.ok().body(postService.findAllPost());
    }

    @DeleteMapping("/{postId}")
    public void Post(@PathVariable Integer postId) {
        postService.deletePost(postId);
    }

    @GetMapping("category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> findPostByCategory(@PathVariable Integer categoryId) {
        return ResponseEntity.ok().body(postService.findPostByCategory(categoryId));
    }

    @GetMapping("user/{userId}/posts")
    public ResponseEntity<List<PostDto>> findPostByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok().body(postService.findPostByUser(userId));
    }
}
