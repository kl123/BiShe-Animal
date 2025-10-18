package com.example.animal_shelet.pojo.Announcement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 公告实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Announcement {
    
    /**
     * 公告ID
     */
    private Integer id;
    
    /**
     * 公告名称/标题
     */
    private String name;
    
    /**
     * 公告内容
     */
    private String content;
    
    /**
     * 公告图片URL（可选）
     */
    private String imageUrl;
    
    /**
     * 公告状态（0:下架, 1:上架）
     */
    private Integer status;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
    
    /**
     * 创建者ID
     */
    private Integer creatorId;
}