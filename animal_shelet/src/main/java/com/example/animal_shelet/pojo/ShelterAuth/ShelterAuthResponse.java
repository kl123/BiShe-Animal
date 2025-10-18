package com.example.animal_shelet.pojo.ShelterAuth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 流浪所认证响应DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShelterAuthResponse {
    
    /**
     * 申请ID
     */
    private Integer id;
    
    /**
     * 用户ID
     */
    private Integer userId;
    
    /**
     * 流浪所名称
     */
    private String shelterName;
    
    /**
     * 联系人姓名
     */
    private String contactPerson;
    
    /**
     * 营业执照
     */
    private String businessLicense;
    
    /**
     * 资质文件
     */
    private String qualificationFile;
    
    /**
     * 申请状态（0:待审核, 1:审核通过, 2:审核不通过）
     */
    private Integer status;
    
    /**
     * 状态描述
     */
    private String statusStr;
    
    /**
     * 申请说明
     */
    private String description;
    
    /**
     * 审核意见
     */
    private String auditReason;
    
    /**
     * 申请时间
     */
    private LocalDateTime createdAt;
    
    /**
     * 审核时间
     */
    private LocalDateTime auditTime;
}