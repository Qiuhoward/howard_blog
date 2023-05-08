package com.example.login.controller;

import com.example.login.dto.CustomConstant;
import com.example.login.dto.blog.PostDto;
import com.example.login.dto.blog.PostResponse;
import com.example.login.exception.ApiResponse;
import com.example.login.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <文章相關API></文章相關API>
 */

@RestController
@Tag(name = "文章(Post)")

@RequestMapping(value = "post")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/user/{userId}/category/{categoryId}/post")
    @Operation(summary = "新增文章")
    public ResponseEntity<PostDto> addPost(@RequestBody PostDto postDto, @PathVariable String userId, @PathVariable String categoryId) {
        return new ResponseEntity<>(postService.addPost(postDto, Integer.parseInt(categoryId), Integer.parseInt(userId)), HttpStatus.CREATED);
    }


    @PutMapping("/{postId}")
    @Operation(summary = "編輯文章")
    public ResponseEntity<PostDto> editPost(
            @RequestParam(value = "content") String content,
            @RequestParam(value = "title") String title,
            @PathVariable String postId) {

        return new ResponseEntity<>(postService.editPost(Integer.parseInt(postId) , content, title), HttpStatus.OK);
    }

    @GetMapping("/")
    @Operation(summary = "搜尋所有文章")
    public ResponseEntity<PostResponse> findAllPost(
            @RequestParam(value = "pageSize", defaultValue = CustomConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "pageNumber", defaultValue = CustomConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "sortBy", defaultValue = CustomConstant.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = CustomConstant.SORT_DIC, required = false) String sortDir
    ) {

        return ResponseEntity.ok().body(postService.findAllPost(pageNumber, pageSize, sortBy, sortDir));
    }

    @DeleteMapping("/{postId}")
//    @PreAuthorize("")
    @Operation(summary = "刪除文章")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable String postId) {
        postService.deletePost(Integer.parseInt(postId) );
        return ResponseEntity.ok().body(new ApiResponse("deleted", true));
    }

    @GetMapping("category/{categoryId}/posts")
    @Operation(summary = "搜尋特定類別文章")
    public ResponseEntity<List<PostDto>> findPostByCategory(@PathVariable String categoryId) {
        return ResponseEntity.ok().body(postService.findPostByCategory(Integer.parseInt(categoryId) ));
    }

    @GetMapping("user/{userId}/posts")
    @Operation(summary = "搜尋特定使用者文章")
    public ResponseEntity<List<PostDto>> findPostByUser(@PathVariable String userId) {
        return ResponseEntity.ok().body(postService.findPostByUser(Integer.parseInt(userId) ));
    }
    @PostMapping("user/{userId}/posts/desc")
    @Operation(summary = "搜尋特定使用者文章")
    public ResponseEntity<List<PostDto>> findPostByUserAndDesc(@PathVariable String userId) {
        return ResponseEntity.ok().body(postService.findPostByUserAndDesc(Integer.parseInt(userId) ));
    }
    @GetMapping("/keyword")
    @Operation(summary = "關鍵字搜尋文章")
    public ResponseEntity<List<PostDto>> findPostByTitle(@RequestParam(value = "keyword") String keyword) {
        return ResponseEntity.ok().body(postService.findPostByTitle(keyword));
    }
}
