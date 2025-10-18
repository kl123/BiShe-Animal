package com.example.animal_shelet.service;

import com.example.animal_shelet.mapper.StatisticsMapper;
import com.example.animal_shelet.pojo.Statistics.PlatformStatistics;
import com.example.animal_shelet.pojo.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 平台统计数据服务层
 */
@Service
public class StatisticsService {
    
    @Autowired
    private StatisticsMapper statisticsMapper;
    
    /**
     * 获取平台统计数据
     * @return 包含所有统计数据的结果
     */
    public Result getPlatformStatistics() {
        try {
            // 获取各项统计数据
            Integer availablePetsCount = statisticsMapper.getAvailablePetsCount();
            Integer completedAdoptionsCount = statisticsMapper.getCompletedAdoptionsCount();
            Integer monthlyApprovalCount = statisticsMapper.getMonthlyApprovalCount();
            Integer sheltersCount = statisticsMapper.getSheltersCount();
            Integer userCommentsCount = statisticsMapper.getUserCommentsCount();
            
            // 创建统计数据对象
            PlatformStatistics statistics = new PlatformStatistics(
                availablePetsCount,
                completedAdoptionsCount,
                monthlyApprovalCount,
                sheltersCount,
                userCommentsCount
            );
            
            return Result.success(statistics);
        } catch (Exception e) {
            return Result.error("获取平台统计数据失败: " + e.getMessage());
        }
    }
}