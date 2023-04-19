package com.example.login.service;


import com.example.login.dao.post.Post;
import com.example.login.dao.post.PostRepo;
import com.example.login.dao.user.UserRepo;
import com.example.login.dto.blog.PostDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.util.List;

@Log4j2
@Service
public class PostService implements BasicService {

    private final PostRepo postRepo;

    private final UserRepo userRepo;

    public PostService(PostRepo postRepo, UserRepo userRepo) {
        this.postRepo = postRepo;
        this.userRepo = userRepo;
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
                    e.setPostId(post.getPostId());
                });
        return status;
    }


    @Override
    public void delete(int postId, String name) {
        log.info("刪除PostId {} 文章", postId);
        postRepo.deletePostByPostIdAndPostPeople(postId, name).orElseThrow();
    }


    public String edit(String name, int postId, String content, String title) {
        var post = postRepo.findPostByPostIdAndPostPeople(postId, name).orElseThrow();
        post.setContent(content);
        post.setTitle(title);
        postRepo.save(post);
        return "修改成功";
    }

    @Override
    public List<Post> findAll() {
        return postRepo.findAll();
    }
}
