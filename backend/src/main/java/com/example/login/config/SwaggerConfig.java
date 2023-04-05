package com.example.login.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 要有登入機制，讓管理員能夠登入到管理後台 (差前端頁面以及api token驗證)
 * 身為一個管理員，要能夠新增文章、編輯文章、刪除文章
 * 新增文章時要有標題以及內文（串接 CKEditor）
 * 身為一個訪客，在首頁要能看到最新的五篇文章
 * 全文列表功能，能看到所有文章
 * 文章分類功能，能在後台管理分類列表，並可在分類顯示畫面依據類別顯示不同文章
 * read more 功能，點選之後可以觀看完整文章
 * 分頁功能
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI swaggerAPI() {
        return new OpenAPI().info(
                new Info()
                        .title("Swagger_API")
                        .version("0.0.1")
                        .description("spring_boot_API"));
    }
}
