package com.solution.hacktrail.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    private final ChatClient.Builder chatClientBuilder;

    public ChatClientConfig(ChatClient.Builder chatClientBuilder) {
        this.chatClientBuilder = chatClientBuilder;
    }

    @Bean
    public ChatClient chatClient() {
        return chatClientBuilder.build();
    }
}
