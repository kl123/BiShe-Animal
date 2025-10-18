package com.example.animal_shelet.mapper;

import com.example.animal_shelet.pojo.UserCenter.UserProfile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户中心Mapper接口
 */
@Mapper
public interface UserCenterMapper {
    
    /**
     * 获取用户基本信息
     * @param userId 用户ID
     * @return 用户基本信息
     */
    UserProfile getUserBasicInfo(@Param("userId") Integer userId);
    
    /**
     * 获取用户领养宠物数量
     * @param userId 用户ID
     * @return 领养宠物数量
     */
    Integer getUserAdoptedPetsCount(@Param("userId") Integer userId);
    
    /**
     * 检查用户是否为流浪所账号
     * @param userId 用户ID
     * @return 流浪所信息，如果不是流浪所账号则返回null
     */
    String getShelterNameByUserId(@Param("userId") Integer userId);
    
    /**
     * 获取流浪所已售出动物数量
     * @param userId 用户ID (流浪所账号)
     * @return 已售出动物数量
     */
    Integer getShelterSoldAnimalsCount(@Param("userId") Integer userId);
    
    /**
     * 获取流浪所正在售出动物数量
     * @param userId 用户ID (流浪所账号)
     * @return 正在售出动物数量
     */
    Integer getShelterAvailableAnimalsCount(@Param("userId") Integer userId);
    
    /**
     * 获取用户发布帖子数量
     * @param userId 用户ID
     * @return 发布帖子数量
     */
    Integer getUserPostsCount(@Param("userId") Integer userId);
}