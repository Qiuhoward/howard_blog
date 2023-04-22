package com.example.login.service;

import com.example.login.dao.post.Category;
import com.example.login.dao.post.CategoryRepo;
import com.example.login.dao.post.Post;
import com.example.login.dao.post.PostRepo;
import com.example.login.dao.user.User;
import com.example.login.dao.user.UserRepo;
import com.example.login.dto.blog.PostDto;
import com.example.login.exception.ResourceNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class PostServiceImp implements PostService {

    private final PostRepo postRepo;
    private final UserRepo userRepo;
    private final ModelMapper mapper;

    private final CategoryRepo categoryRepo;

    public PostServiceImp(PostRepo postRepo, UserRepo userRepo, ModelMapper mapper, CategoryRepo categoryRepo) {
        this.postRepo = postRepo;
        this.userRepo = userRepo;
        this.mapper = mapper;
        this.categoryRepo = categoryRepo;
    }


    public Boolean addPost(PostDto request) {
        log.info("name:{}", request.getAuthor());
        boolean status = userRepo.findUserByName(request.getAuthor()).isPresent();
        log.info("status:{}", status);
        userRepo.findUserByName(request.getAuthor())
                .ifPresent((e) -> {
                    var post = Post.builder()
                            .postPeople(request.getAuthor())
                            .createAt(Date.from(Instant.now()))
                            .title(request.getTitle())
                            .content(request.getContent())
                            .build();
                    postRepo.save(post);
                });

        return status;
    }


    public void deletePost(int postId) {
        log.info("刪除PostId {} 文章", postId);
        postRepo.deletePostByPostId(postId).orElseThrow();
    }


    public String edit(String name, int postId, String content, String title) {
        var post = postRepo.findPostByPostIdAndPostPeople(postId, name).orElseThrow();
        post.setContent(content);
        post.setTitle(title);
        postRepo.save(post);
        return "修改成功";
    }


    public List<PostDto> findAllPost() {
        return postRepo.findAll()
                .stream()
                .map((post) -> this.mapper.map(post, PostDto.class))
                .collect(Collectors.toList());
    }


    public List<PostDto> findPostByCategory(int categoryId) {
        categoryRepo.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException(Category.class, "categoryId", categoryId));

        return postRepo.findByCategoryId(categoryId)
                .stream()
                .map((post) -> this.mapper.map(post, PostDto.class))
                .collect(Collectors.toList());
    }


    public List<PostDto> findPostByUser(Integer userId) {
        userRepo.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(User.class, "userId", userId));

        return postRepo.findByUser(userId)
                .stream()
                .map((post) -> this.mapper.map(post, PostDto.class))
                .collect(Collectors.toList());
    }

}
