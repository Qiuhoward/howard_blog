package com.example.login.dto.blog;

import lombok.*;

/**
 * 暫定post都用這個dto
 */


@Getter
@Builder
@AllArgsConstructor

public class PostDto extends BasicDto {
    private String author;
    private String content;
    private String title;

    public PostDto() {
        super();
    }

}
