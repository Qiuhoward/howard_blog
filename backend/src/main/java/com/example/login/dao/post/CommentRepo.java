package com.example.login.dao.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CommentRepo extends JpaRepository<Comment,Integer> {



   List<Comment> findCommentByPost(Post post);


    List<Comment> findByContentContaining(String keyword);
}
