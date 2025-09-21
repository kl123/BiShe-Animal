package com.example.animal_shelet.utils.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(InterceptorConfig.class);

    // 直接注入配置属性
    @Value("${jwt.interceptor.enabled:true}")
    private boolean jwtInterceptorEnabled;

    @Bean
    @ConditionalOnProperty(name = "jwt.interceptor.enabled",
                          havingValue = "true",
                          matchIfMissing = true)
    public JWTInterceptors jwtInterceptors() {
        return new JWTInterceptors();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (jwtInterceptorEnabled) {
            logger.info("JWT拦截器已成功部署，开始拦截请求");
            registry.addInterceptor(jwtInterceptors())
                    .addPathPatterns("/**")
                    .excludePathPatterns("/user/login", "/user/register", "/static/**","/utils/**");
        } else {
            logger.info("JWT拦截器已禁用（通过配置开关）");
        }
    }
}
