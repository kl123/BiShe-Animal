package com.example.animal_shelet.mapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * 平台统计数据Mapper接口
 */
@Mapper
public interface StatisticsMapper {
    
    /**
     * 获取平台待领养的宠物个数
     * @return 待领养宠物数量
     */
    Integer getAvailablePetsCount();
    
    /**
     * 获取平台已经完成对接领养个数
     * @return 已完成领养数量
     */
    Integer getCompletedAdoptionsCount();
    
    /**
     * 获取平台月审批量数
     * @return 当月审批数量
     */
    Integer getMonthlyApprovalCount();
    
    /**
     * 获取平台入住流浪所数
     * @return 流浪所数量
     */
    Integer getSheltersCount();
    
    /**
     * 获取平台用户评论数
     * @return 用户评论总数
     */
    Integer getUserCommentsCount();
}