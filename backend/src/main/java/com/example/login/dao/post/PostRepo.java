package com.example.login.dao.post;

import com.example.login.dao.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepo extends JpaRepository<Post,Integer> {
    Optional<Post> deletePostByPostId(Integer postId);
    Optional<Post>findPostByPostIdAndPostPeople(Integer postId, String name);

    Optional<Post> findByUser(User user);

        Optional<Post>findByCategory(Category category);
}
