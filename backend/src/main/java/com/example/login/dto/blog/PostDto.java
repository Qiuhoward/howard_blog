package com.example.login.dto.blog;

import lombok.Builder;
import lombok.Getter;

/**
 * 暫定post都用這個dto
 */

@Builder
@Getter
public class PostDto extends BasicDto {
    public PostDto() {
        super();
    }

}
