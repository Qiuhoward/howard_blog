package com.example.login.dao.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CommentRepo extends JpaRepository<Comment,Integer> {

   void deleteCommentByCommentIdAndCommentPeople(Integer commentId, String commentPeople);
}
