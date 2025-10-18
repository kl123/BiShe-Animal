package com.example.animal_shelet.pojo.ShelterAuth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 流浪所认证申请DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShelterAuthRequest {
    
    /**
     * 流浪所名称
     */
    private String shelterName;
    
    /**
     * 联系人姓名
     */
    private String contactPerson;
    
    /**
     * 营业执照（Base64编码或文件路径）
     */
    private String businessLicense;
    
    /**
     * 资质文件（Base64编码或文件路径）
     */
    private String qualificationFile;
    
    /**
     * 联系电话（可选，从用户信息获取）
     */
    private String contactPhone;
    
    /**
     * 联系邮箱（可选，从用户信息获取）
     */
    private String contactEmail;
    
    /**
     * 申请说明
     */
    private String description;
}