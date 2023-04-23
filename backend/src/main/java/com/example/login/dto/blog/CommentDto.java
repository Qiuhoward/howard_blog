package com.example.login.dto.blog;


import lombok.Getter;
import lombok.Setter;


/**
 * <文章留言封包></文章留言封包>
 */
@Getter
@Setter
public class CommentDto {
    private int commentId;
    private String comment;
    private String commentPeople;
}
