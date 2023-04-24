package com.example.login.dto.blog;


import com.example.login.dao.post.Category;
import com.example.login.dao.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;



/**
 * <文章傳送封包></文章傳送封包>
 */

@Getter
@Builder
@AllArgsConstructor
public class PostDto extends BasicDto {
    private String author;
    private String content;
    private String title;
    private CategoryDto category;
//    private UserDto user;

    public PostDto() {
        super();
    }

}
