package com.example.login.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 要有登入機制，讓管理員能夠登入到管理後台
 * 新增文章時要有標題以及內文（串接 CKEditor）
 * 身為一個訪客，在首頁要能看到最新的五篇文章
 * 全文列表功能，能看到所有文章
 * 文章分類功能，能在後台管理分類列表，並可在分類顯示畫面依據類別顯示不同文章
 * read more 功能，點選之後可以觀看完整文章
 * 分頁功能
 */

/**
 * <Swagger URI><a href="http://localhost:8080/swagger-ui/index.html">
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI swaggerAPI() {
        return new OpenAPI().info(
                new Info()
                        .title("Howard_Blog_API")
                        .version("0.0.1")
                        .description("This document is provide to frontend engineer to know What's API need??"));
    }
}
