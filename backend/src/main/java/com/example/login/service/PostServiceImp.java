package com.example.login.service;

import com.example.login.dao.post.Category;
import com.example.login.dao.post.CategoryRepo;
import com.example.login.dao.post.Post;
import com.example.login.dao.post.PostRepo;
import com.example.login.dao.user.User;
import com.example.login.dao.user.UserRepo;
import com.example.login.dto.blog.PostDto;
import com.example.login.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
public class PostServiceImp implements PostService {

    private final PostRepo postRepo;
    private final UserRepo userRepo;
    private final ModelMapper mapper;
    private final CategoryRepo categoryRepo;


    public PostDto addPost(PostDto postDto, Integer categoryId, Integer userId) {
        log.info("request:{}", postDto);

        var user = userRepo.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(User.class, "userId", userId));

        var category = categoryRepo.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException(Category.class, "categoryId", categoryId));
        var post = this.mapper.map(postDto, Post.class);

        post.setCategory(category);
        post.setUser(user);
        postRepo.save(post);

        return this.mapper.map(post, PostDto.class);
    }

    public void deletePost(int postId) {
        log.info("刪除PostId {} 文章", postId);
        postRepo.deletePostByPostId(postId).orElseThrow();
    }


    public PostDto editPost(String name, Integer postId, String content, String title) {
        var post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException(Post.class, "postId", postId)
        );
        post.setContent(content);
        post.setTitle(title);
        postRepo.save(post);
        return this.mapper.map(post, PostDto.class);
    }


    public List<PostDto> findAllPost(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);//自訂義頁數跟大小
        Page<Post> pagePost = postRepo.findAll(pageable);
        List<Post> postList = pagePost.getContent();
        //搭配Sort來對資料庫做分頁及排序查
        return postList
                .stream()
                .map((post) -> this.mapper.map(post, PostDto.class))
                .collect(Collectors.toList());
    }


    public List<PostDto> findPostByCategory(int categoryId) {
        var category = categoryRepo.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException(Category.class, "categoryId", categoryId));

        return postRepo.findByCategory(category)
                .stream()
                .map((post) -> this.mapper.map(post, PostDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDto> findPostByUser(int userId) {
        var user = userRepo.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(User.class, "userId", userId));

        return postRepo.findByUser(user)
                .stream()
                .map((post) -> this.mapper.map(post, PostDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDto> findPostByTitle(String keyword) {
        Optional<Post> postOptional = postRepo.findByTitleContaining(keyword);

        return postOptional
                .stream()
                .map((post) -> this.mapper.map(post, PostDto.class)).toList();
    }

}

