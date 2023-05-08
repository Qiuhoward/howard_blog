package com.example.login.dao.post;


import com.example.login.dao.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;
    //可移出優化
    private String author;
    private String title;
    private Date createAt;
    private String content;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id" )
    private Category category;

    @OneToMany(mappedBy = "post" , cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
    private Set<Comment> comment=new HashSet<>();


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
