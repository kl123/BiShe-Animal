package com.example.animal_shelet.pojo.Animal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 智能匹配请求实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchRequest {
    private String keywords; // 用户提供的关键词
    private String preferences; // 用户偏好描述
    private String livingEnvironment; // 居住环境
    private String experience; // 养宠经验
    private boolean enabled;//是否启用AI匹配
}