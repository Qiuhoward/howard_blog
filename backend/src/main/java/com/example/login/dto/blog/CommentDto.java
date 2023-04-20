package com.example.login.dto.blog;


import lombok.Data;

@Data
public class CommentDto {
    private int postId;
    private String comment;
    private String commentPeople;
}
