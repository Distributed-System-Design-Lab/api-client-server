package com.distributed_system_design_lab.api_client_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

/**
 * 配置應用程序的安全設置。
 * 
 * @author vinskao
 * @version 0.1
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    /**
     * 配置安全過濾鏈 bean。
     *
     * @param http HttpSecurity 配置對象
     * @return 配置好的 SecurityFilterChain 實例
     * @throws Exception 配置過程中可能拋出的異常
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 配置安全過濾鏈
        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated()) // 任何請求都需要身份驗證
                .oauth2Login(oauth2Login -> oauth2Login.loginPage("/oauth2/authorization/articles-client-oidc")) // 配置OAuth2登錄頁面
                .oauth2Client(withDefaults()); // 使用默認的 OAuth2 客戶端配置
        return http.build(); // 構建 SecurityFilterChain 實例
    }
}