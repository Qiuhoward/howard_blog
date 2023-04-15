package com.example.login.dao.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepo extends JpaRepository<Post,Integer> {
    Optional<Post> deletePostByPostIdAndPostPeople(Integer postId, String name);
    Optional<Post>findPostByPostIdAndPostPeople(Integer postId, String name);

}
