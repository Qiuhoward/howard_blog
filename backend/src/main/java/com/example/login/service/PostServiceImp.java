package com.example.login.service;

import com.example.login.dao.post.Category;
import com.example.login.dao.post.CategoryRepo;
import com.example.login.dao.post.Post;
import com.example.login.dao.post.PostRepo;
import com.example.login.dao.user.User;
import com.example.login.dao.user.UserRepo;
import com.example.login.dto.blog.PostDto;
import com.example.login.dto.blog.PostResponse;
import com.example.login.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
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


    public PostDto addPost(PostDto postDto, Integer categoryId, Integer userId) {
        log.info("新增文章 ->用戶Id:{}", userId);

        var user = userRepo.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(User.class, "userId", userId));

        var category = categoryRepo.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException(Category.class, "categoryId", categoryId));
        var post = this.mapper.map(postDto, Post.class);

        post.setCategory(category);
        post.setUser(user);
        post.setCreateAt(new Date());
        post.setAuthor(user.getName());
        post = postRepo.save(post);
        postDto = this.mapper.map(post, PostDto.class);

        return postDto;
    }

    public void deletePost(Integer postId) {
        log.info("刪除PostId -> 文章Id:{}", postId);
        var post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException(Post.class, "postId", postId)
        );
        postRepo.delete(post);
    }


    public PostDto editPost(Integer postId, String content, String title) {
        log.info("編輯指定文章");
        var post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException(Post.class, "postId", postId)
        );

        post.setContent(content);
        post.setTitle(title);
        System.out.println(post.getTitle() + post.getContent());
        post = postRepo.save(post);

        return this.mapper.map(post, PostDto.class);
    }


    public PostResponse findAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDic) {
        log.info("尋找所有文章用{}做排序", sortBy);
        Sort sort = sortDic.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);//自訂義頁數跟大小
        Page<Post> pagePost = postRepo.findAll(pageable);
        List<Post> postList = pagePost.getContent();
        //搭配Sort來對資料庫做分頁及排序查
        var postDtoList = postList
                .stream()
                .map((post) -> this.mapper.map(post, PostDto.class))
                .toList();
//        postDtoList.forEach((postDto)->postDto.setName());
        return new PostResponse(postDtoList, pagePost.getNumber(), pagePost.getSize(),
                (int) pagePost.getTotalElements(), pagePost.getTotalPages(), pagePost.isLast());
    }


    public List<PostDto> findPostByCategory(Integer categoryId) {
        log.info("分類尋找文章 ->分類id:{}", categoryId);
        var category = categoryRepo.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException(Category.class, "categoryId", categoryId));

        return postRepo.findByCategory(category)
                .stream()
                .map((post) -> this.mapper.map(post, PostDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDto> findPostByUser(int userId) {
        log.info("用戶尋找文章 -> 用戶id {}", userId);
        var user = userRepo.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(User.class, "userId", userId));

        return postRepo.findByUser(user)
                .stream()
                .map((post) -> this.mapper.map(post, PostDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDto> findPostByTitle(String keyword) {
        log.info("關鍵字尋找文章 -> keyword:{}", keyword);
        List<Post> postOptional = postRepo.findByTitleContaining(keyword);
        Comparator<Post> postComparator = Comparator.comparing(Post::getPostId);
        return postOptional
                .stream()
                .sorted(postComparator.reversed())
                .map((post) -> this.mapper.map(post, PostDto.class)).toList();
    }

    @Override
    public List<PostDto> findPostByUserAndDesc(Integer userId) {
        log.info("使用者尋找文章並且按照降冪排序");
        var user = userRepo.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(User.class, "userId", userId));
        Comparator<Post> postComparator = Comparator.comparing(Post::getPostId);
        return postRepo.findByUser(user)
                .stream()
                .sorted(postComparator.reversed())
                .map((post) -> this.mapper.map(post, PostDto.class))
                .collect(Collectors.toList());
    }
}


