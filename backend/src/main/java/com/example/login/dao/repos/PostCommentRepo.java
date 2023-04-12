package com.example.login.dao.repos;

import com.example.login.dao.entities.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostCommentRepo extends JpaRepository<PostComment,Integer> {
}
