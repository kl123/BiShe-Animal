package com.example.animal_shelet.pojo.UserCenter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户个人中心信息DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {
    
    /**
     * 用户ID
     */
    private Integer userId;
    
    /**
     * 用户名称
     */
    private String username;
    
    /**
     * 用户领养宠物数目
     */
    private Integer adoptedPetsCount;
    
    /**
     * 用户是否为流浪所账号 (true: 是流浪所账号, false: 普通用户)
     */
    private Boolean isShelterAccount;
    
    /**
     * 流浪所账号已售出动物数 (仅流浪所账号有效)
     */
    private Integer soldAnimalsCount;
    
    /**
     * 流浪所账号正在售出动物数 (仅流浪所账号有效)
     */
    private Integer availableAnimalsCount;
    
    /**
     * 发布帖子数
     */
    private Integer postsCount;
    
    /**
     * 流浪所名称 (仅流浪所账号有效)
     */
    private String shelterName;
}