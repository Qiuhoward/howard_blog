package com.example.login.dto.blog;

import lombok.*;

import java.util.Set;


/**
 * <文章傳送封包></文章傳送封包>
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDto  {
    private Integer postId;
    private String content;
    private String title;
    private CategoryDto category;
    private Set<CommentDto> commentDtoSet;
//    private UserDto user;



}
