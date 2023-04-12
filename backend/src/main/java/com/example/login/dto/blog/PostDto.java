package com.example.login.dto.blog;

import lombok.Builder;
import lombok.Getter;

/**
 * 暫定post都用這個dto
 */


@Getter
@Builder
public class PostDto extends BasicDto {
    private String postPeople;
    private String content;
    private String Title;

    public PostDto() {
        super();
    }

}
