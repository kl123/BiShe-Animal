package com.example.animal_shelet.mapper;

import com.example.animal_shelet.pojo.ShelterAuth.ShelterAuthRequest;
import com.example.animal_shelet.pojo.ShelterAuth.ShelterAuthResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 流浪所认证Mapper接口
 */
@Mapper
public interface ShelterAuthMapper {
    
    /**
     * 提交流浪所认证申请
     * @param userId 用户ID
     * @param request 认证申请信息
     * @return 影响行数
     */
    int insertShelterAuthApplication(@Param("userId") Integer userId, @Param("request") ShelterAuthRequest request);
    
    /**
     * 检查用户是否已有待审核的申请
     * @param userId 用户ID
     * @return 待审核申请数量
     */
    int countPendingApplicationsByUserId(@Param("userId") Integer userId);
    
    /**
     * 检查用户是否已经是流浪所账号
     * @param userId 用户ID
     * @return 流浪所记录数量
     */
    int countShelterByUserId(@Param("userId") Integer userId);
    
    /**
     * 根据用户ID获取认证申请记录
     * @param userId 用户ID
     * @return 认证申请记录列表
     */
    List<ShelterAuthResponse> getShelterAuthApplicationsByUserId(@Param("userId") Integer userId);
    
    /**
     * 根据申请ID获取认证申请详情
     * @param applicationId 申请ID
     * @return 认证申请详情
     */
    ShelterAuthResponse getShelterAuthApplicationById(@Param("applicationId") Integer applicationId);
    
    /**
     * 获取所有待审核的认证申请（管理员用）
     * @return 待审核申请列表
     */
    List<ShelterAuthResponse> getAllPendingApplications();
    
    /**
     * 更新申请状态（审核用）
     * @param applicationId 申请ID
     * @param status 新状态
     * @param auditReason 审核意见
     * @return 影响行数
     */
    int updateApplicationStatus(@Param("applicationId") Integer applicationId, 
                               @Param("status") Integer status, 
                               @Param("auditReason") String auditReason);
    
    /**
     * 审核通过后创建流浪所记录
     * @param applicationId 申请ID
     * @return 影响行数
     */
    int createShelterFromApplication(@Param("applicationId") Integer applicationId);
}