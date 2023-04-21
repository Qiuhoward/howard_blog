package com.example.login.dao.post;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;


@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
@Entity
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;
    private String content;
    private String commentPeople;
    private Date createAt;
    private Integer postId;
    private Integer userId;

}
