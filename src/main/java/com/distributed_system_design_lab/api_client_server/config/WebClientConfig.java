package com.distributed_system_design_lab.api_client_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;

/**
 * 配置 WebClient 和 OAuth2 授權客戶端管理器的類。
 * 
 * @version 0.1
 * @author vinskao
 */
@Configuration
public class WebClientConfig {

        /**
         * 創建並配置 WebClient bean。
         *
         * @param authorizedClientManager 授權客戶端管理器
         * @return 配置好的 WebClient 實例
         */
        @Bean
        WebClient webClient(OAuth2AuthorizedClientManager authorizedClientManager) {
                // 配置 ServletOAuth2AuthorizedClientExchangeFilterFunction 以支持 OAuth2
                ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2Client = new ServletOAuth2AuthorizedClientExchangeFilterFunction(
                                authorizedClientManager);
                return WebClient.builder()
                                .apply(oauth2Client.oauth2Configuration())
                                .build(); // 構建 WebClient 實例
        }

        /**
         * 創建並配置 OAuth2 授權客戶端管理器 bean。
         *
         * @param clientRegistrationRepository 用於註冊客戶端的存儲庫
         * @param authorizedClientRepository   授權客戶端存儲庫
         * @return 配置好的 OAuth2 授權客戶端管理器
         */
        @Bean
        OAuth2AuthorizedClientManager authorizedClientManager(
                        ClientRegistrationRepository clientRegistrationRepository,
                        OAuth2AuthorizedClientRepository authorizedClientRepository) {
                // 配置授權客戶端提供程序，支持授權碼和刷新令牌
                OAuth2AuthorizedClientProvider authorizedClientProvider = OAuth2AuthorizedClientProviderBuilder
                                .builder()
                                .authorizationCode()
                                .refreshToken()
                                .build();
                // 創建默認的 OAuth2 授權客戶端管理器
                DefaultOAuth2AuthorizedClientManager authorizedClientManager = new DefaultOAuth2AuthorizedClientManager(
                                clientRegistrationRepository, authorizedClientRepository);
                authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

                return authorizedClientManager;
        }
}
