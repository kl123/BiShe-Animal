package com.example.animal_shelet.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import lombok.Data;

import java.time.Duration;

/**
 * AI配置类
 */
@Configuration
@ConfigurationProperties(prefix = "ai.api")
@Data
public class AIConfig {
    
    /**
     * AI API URL
     */
    private String url = "https://dashscope.aliyuncs.com/compatible-mode/v1";
    
    /**
     * API密钥
     */
    private String key = "sk-583a5dec7c804c32b0527ce442e04556";
    
    /**
     * 模型名称
     */
    private String model = "qwen3-max";

    
    /**
     * 请求超时时间（毫秒）
     */
    private int timeout = 30000;
}