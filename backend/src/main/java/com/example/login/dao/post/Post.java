package com.example.login.dao.post;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
@Entity
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;
    private String content;
    private String postPeople;
    private String title;
    private Date createAt;



//    public void addPost(PostDto request, PostRepo repo) {
//       this.createAt=System.currentTimeMillis();
//       this.postPeople= request.getPostPeople();
//       this.title=request.getTitle();
//      //傳給留言的add comment把留言存起來 =request.getId();
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Post post = (Post) o;
        return postId != null && Objects.equals(postId, post.postId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
