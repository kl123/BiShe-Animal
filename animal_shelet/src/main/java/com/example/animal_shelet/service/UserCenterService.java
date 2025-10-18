package com.example.animal_shelet.service;

import com.example.animal_shelet.mapper.UserCenterMapper;
import com.example.animal_shelet.pojo.UserCenter.UserProfile;
import com.example.animal_shelet.pojo.result.Result;
import com.example.animal_shelet.utils.jwt.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 用户中心Service层
 */
@Slf4j
@Service
public class UserCenterService {
    
    @Autowired
    private UserCenterMapper userCenterMapper;
    
    /**
     * 获取用户个人中心信息
     * @param token JWT令牌
     * @return 用户个人中心信息
     */
    public Result getUserProfile(String token) {
        try {
            // 解析token获取用户ID
            Map<String, String> tokenInfo = JWTUtils.getTokenInfo(token);
            String userIdStr = tokenInfo.get("userId");
            Integer userId = Integer.parseInt(userIdStr);
            
            log.info("获取用户个人中心信息，用户ID: {}", userId);
            
            // 获取用户基本信息
            UserProfile userProfile = userCenterMapper.getUserBasicInfo(userId);
            if (userProfile == null) {
                return Result.error("用户不存在");
            }
            
            // 获取用户领养宠物数量
            Integer adoptedPetsCount = userCenterMapper.getUserAdoptedPetsCount(userId);
            userProfile.setAdoptedPetsCount(adoptedPetsCount != null ? adoptedPetsCount : 0);
            
            // 检查是否为流浪所账号
            String shelterName = userCenterMapper.getShelterNameByUserId(userId);
            boolean isShelterAccount = shelterName != null;
            userProfile.setIsShelterAccount(isShelterAccount);
            userProfile.setShelterName(shelterName);
            
            // 如果是流浪所账号，获取流浪所相关统计
            if (isShelterAccount) {
                Integer soldAnimalsCount = userCenterMapper.getShelterSoldAnimalsCount(userId);
                Integer availableAnimalsCount = userCenterMapper.getShelterAvailableAnimalsCount(userId);
                
                userProfile.setSoldAnimalsCount(soldAnimalsCount != null ? soldAnimalsCount : 0);
                userProfile.setAvailableAnimalsCount(availableAnimalsCount != null ? availableAnimalsCount : 0);
            } else {
                userProfile.setSoldAnimalsCount(0);
                userProfile.setAvailableAnimalsCount(0);
            }
            
            // 获取用户发布帖子数量
            Integer postsCount = userCenterMapper.getUserPostsCount(userId);
            userProfile.setPostsCount(postsCount != null ? postsCount : 0);
            
            log.info("用户个人中心信息获取成功: {}", userProfile);
            return Result.success(userProfile);
            
        } catch (Exception e) {
            log.error("获取用户个人中心信息失败", e);
            return Result.error("获取用户信息失败");
        }
    }
}