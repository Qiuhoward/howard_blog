package com.example.login.controller;

import com.example.login.dao.post.Post;
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
    public String editPost(String name, int postId, String content, String title) {

        return postService.edit(name, postId, content, title);
    }

    @GetMapping("/find_all")
    public List<Post> findAll() {

        return postService.findAll();
    }

    @DeleteMapping("/delete")
    public void Post(int postId, String name) {
        postService.delete(postId, name);
    }

}
