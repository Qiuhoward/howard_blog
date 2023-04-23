package com.example.login.dto.blog;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
    private int commentId;
    private String comment;
    private String commentPeople;
}
