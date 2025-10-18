package com.example.animal_shelet.pojo.Statistics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 平台统计数据DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlatformStatistics {
    
    /**
     * 平台待领养的宠物个数
     */
    private Integer availablePetsCount;
    
    /**
     * 平台已经完成对接领养个数
     */
    private Integer completedAdoptionsCount;
    
    /**
     * 平台月审批量数
     */
    private Integer monthlyApprovalCount;
    
    /**
     * 平台入住流浪所数
     */
    private Integer sheltersCount;
    
    /**
     * 平台用户评论数
     */
    private Integer userCommentsCount;
}