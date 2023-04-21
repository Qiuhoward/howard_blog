package com.example.login.dao.post;


import com.example.login.dao.user.User;
import jakarta.persistence.*;
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
    private Integer categoryId;

    @ManyToOne
    private User user;
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
