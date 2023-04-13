package com.example.login.service;


import com.example.login.dao.entities.Post;
import com.example.login.dao.repos.PostRepo;
import com.example.login.dto.blog.PostDto;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.util.List;


@Service
public class PostService implements BasicService{

    private final PostRepo postRepo;


    public PostService(PostRepo postRepo) {
        this.postRepo = postRepo;

    }


    public String addPost(PostDto request) {
        var post = Post.builder()
                .postPeople(request.getPostPeople())
                .createAt(Date.from(Instant.now()))
                .title(request.getTitle())
                .content(request.getContent())
                .build();

        postRepo.save(post);

        return "新增成功";
    }


//傳給留言的add comment把留言存起來 =request.getId();

    @Override
    public void delete(int postId,String name) {
        postRepo.deletePostByPostIdAndPostPeople(postId,name).orElseThrow();
    }


    public String edit(String name, int postId,String content, String title) {
        var post= postRepo.findPostByPostIdAndPostPeople(postId,name).orElseThrow();
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
