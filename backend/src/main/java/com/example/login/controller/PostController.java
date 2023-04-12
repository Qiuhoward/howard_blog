package com.example.login.controller;

import com.example.login.dao.entities.Post;
import com.example.login.dto.blog.PostDto;
import com.example.login.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <文章窗口></文章窗口>
 */

@RestController
@RequestMapping(value = "post")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("add")
    public String addPost(@RequestBody PostDto postDto) {
        return postService.addPost(postDto);
    }

    @PutMapping("edit")
    public String editPost(String name, int postId, String content, String title) {

        return postService.edit(name, postId, content, title);
    }

    @GetMapping("find_all")
    public List<Post> findAll() {

        return postService.findAll();
    }

    @DeleteMapping("delete")
    public String Post(String name, int postId) {

        return postService.delete(name, postId);
    }

}