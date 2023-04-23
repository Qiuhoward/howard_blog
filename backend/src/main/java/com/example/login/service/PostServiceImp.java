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

import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
public class PostServiceImp implements PostService {

    private final PostRepo postRepo;
    private final UserRepo userRepo;
    private final ModelMapper mapper;
    private final CategoryRepo categoryRepo;


    public String addPost(PostDto request) {
        log.info("name:{}", request.getAuthor());

        userRepo.findUserByName(request.getAuthor()).orElseThrow(
                () -> new ResourceNotFoundException(Post.class, "author", request.getAuthor()));

        var post = Post.builder()
                .postPeople(request.getAuthor())
                .createAt(Date.from(Instant.now()))
                .title(request.getTitle())
                .content(request.getContent())
                .build();

        postRepo.save(post);

        return "新增成功";
    }


    public void deletePost(int postId) {
        log.info("刪除PostId {} 文章", postId);
        postRepo.deletePostByPostId(postId).orElseThrow();
    }


    public String editPost(String name, int postId, String content, String title) {
        var post = postRepo.findPostByPostIdAndPostPeople(postId, name).orElseThrow(
                () -> new ResourceNotFoundException(Post.class, "postId", postId)
        );
        post.setContent(content);
        post.setTitle(title);
        postRepo.save(post);
        return "修改成功";
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

    @Override
    public List<PostDto> findPostByKeyword(String keyword) {
        return null;
    }

}

