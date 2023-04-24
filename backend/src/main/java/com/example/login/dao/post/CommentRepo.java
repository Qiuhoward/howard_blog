package com.example.login.dao.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CommentRepo extends JpaRepository<Comment,Integer> {



   Optional<Comment> findCommentByPost(Post post);


}
