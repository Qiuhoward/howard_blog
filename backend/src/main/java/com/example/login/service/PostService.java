package com.example.login.service;


import com.example.login.dao.entities.Post;
import com.example.login.dao.repos.PostRepo;
import com.example.login.dao.repos.UserRepo;
import com.example.login.dto.blog.PostDto;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PostService implements BasicService{

    private final PostRepo postRepo;
    private final UserRepo userRepo;

    public PostService(PostRepo postRepo, UserRepo userRepo) {
        this.postRepo = postRepo;
        this.userRepo = userRepo;
    }


    public String addPost(PostDto request) {
        var post = Post.builder()
                .postPeople(request.getPostPeople())
                .createAt(System.currentTimeMillis())
                .title(request.getTitle())
                .content(request.getContent())
                .build();

        postRepo.save(post);

        return "修改成功";
    }


//傳給留言的add comment把留言存起來 =request.getId();

    @Override
    public String delete(String name, int postId) {
        postRepo.deletePostByPostIdAndPostPeople(postId,name).orElseThrow();
        return "刪除成功";
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
