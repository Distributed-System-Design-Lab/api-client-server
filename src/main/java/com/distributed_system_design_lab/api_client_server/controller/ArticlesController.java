package com.distributed_system_design_lab.api_client_server.controller;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;

/**
 * 這個控制器負責處理與文章相關的 API 請求。
 * 
 * @author vinskao
 * @version 0.1
 */
@RestController
public class ArticlesController {

    // 定義一個 WebClient 用於發送 HTTP 請求
    private final WebClient webClient;

    /**
     * 使用構造函數注入 WebClient 依賴。
     * 
     * @param webClient 用於發送 HTTP 請求的 WebClient 實例
     */
    public ArticlesController(WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     * 處理 GET /articles 請求，返回文章列表。
     * 
     * @param authorizedClient 綁定的 OAuth2 授權客戶端
     * @return 文章標題列表
     */
    @GetMapping(value = "/articles")
    public String[] getArticles(
            @RegisteredOAuth2AuthorizedClient("articles-client-authorization-code") OAuth2AuthorizedClient authorizedClient) {
        // 使用 WebClient 發送 GET 請求到資源伺服器並攜帶 OAuth2 授權信息
        return this.webClient
                .get() // 發送 GET 請求
                .uri("http://127.0.0.1:8090/articles") // 請求的 URI
                .attributes(
                        ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient(authorizedClient)) // 添加授權客戶端信息
                .retrieve() // 獲取響應
                .bodyToMono(String[].class) // 將響應體轉換為 String 陣列
                .block(); // 阻塞直到獲取到響應結果
    }
}