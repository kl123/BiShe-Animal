package com.example.animal_shelet.service;

import com.example.animal_shelet.mapper.ShelterAuthMapper;
import com.example.animal_shelet.pojo.ShelterAuth.ShelterAuthRequest;
import com.example.animal_shelet.pojo.ShelterAuth.ShelterAuthResponse;
import com.example.animal_shelet.pojo.result.Result;
import com.example.animal_shelet.utils.jwt.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 流浪所认证Service层
 */
@Slf4j
@Service
public class ShelterAuthService {
    
    @Autowired
    private ShelterAuthMapper shelterAuthMapper;
    
    /**
     * 提交流浪所认证申请
     * @param token JWT令牌
     * @param request 认证申请信息
     * @return 申请结果
     */
    @Transactional
    public Result submitShelterAuthApplication(String token, ShelterAuthRequest request) {
        try {
            // 解析token获取用户ID
            Map<String, String> tokenInfo = JWTUtils.getTokenInfo(token);
            String userIdStr = tokenInfo.get("userId");
            Integer userId = Integer.parseInt(userIdStr);
            
            log.info("用户{}提交流浪所认证申请", userId);
            
            // 验证必填字段
            if (request.getShelterName() == null || request.getShelterName().trim().isEmpty()) {
                return Result.error("流浪所名称不能为空");
            }
            if (request.getContactPerson() == null || request.getContactPerson().trim().isEmpty()) {
                return Result.error("联系人姓名不能为空");
            }
            if (request.getBusinessLicense() == null || request.getBusinessLicense().trim().isEmpty()) {
                return Result.error("营业执照不能为空");
            }
            if (request.getQualificationFile() == null || request.getQualificationFile().trim().isEmpty()) {
                return Result.error("资质文件不能为空");
            }
            
            // 检查用户是否已经是流浪所账号
            int existingShelterCount = shelterAuthMapper.countShelterByUserId(userId);
            if (existingShelterCount > 0) {
                return Result.error("您已经是流浪所账号，无需重复申请");
            }
            
            // 检查是否已有待审核的申请
            int pendingCount = shelterAuthMapper.countPendingApplicationsByUserId(userId);
            if (pendingCount > 0) {
                return Result.error("您已有待审核的申请，请耐心等待审核结果");
            }
            
            // 提交申请
            int result = shelterAuthMapper.insertShelterAuthApplication(userId, request);
            if (result > 0) {
                log.info("用户{}流浪所认证申请提交成功", userId);
                return Result.success("申请提交成功，请耐心等待管理员审核");
            } else {
                return Result.error("申请提交失败，请稍后重试");
            }
            
        } catch (Exception e) {
            log.error("提交流浪所认证申请失败", e);
            return Result.error("申请提交失败");
        }
    }
    
    /**
     * 获取用户的认证申请记录
     * @param token JWT令牌
     * @return 申请记录列表
     */
    public Result getUserShelterAuthApplications(String token) {
        try {
            // 解析token获取用户ID
            Map<String, String> tokenInfo = JWTUtils.getTokenInfo(token);
            String userIdStr = tokenInfo.get("userId");
            Integer userId = Integer.parseInt(userIdStr);
            
            log.info("获取用户{}的流浪所认证申请记录", userId);
            
            List<ShelterAuthResponse> applications = shelterAuthMapper.getShelterAuthApplicationsByUserId(userId);
            
            log.info("用户{}共有{}条认证申请记录", userId, applications.size());
            return Result.success(applications);
            
        } catch (Exception e) {
            log.error("获取用户认证申请记录失败", e);
            return Result.error("获取申请记录失败");
        }
    }
    
    /**
     * 获取认证申请详情
     * @param token JWT令牌
     * @param applicationId 申请ID
     * @return 申请详情
     */
    public Result getShelterAuthApplicationDetail(String token, Integer applicationId) {
        try {
            // 解析token获取用户ID
            Map<String, String> tokenInfo = JWTUtils.getTokenInfo(token);
            String userIdStr = tokenInfo.get("userId");
            Integer userId = Integer.parseInt(userIdStr);
            
            log.info("用户{}获取认证申请{}详情", userId, applicationId);
            
            ShelterAuthResponse application = shelterAuthMapper.getShelterAuthApplicationById(applicationId);
            if (application == null) {
                return Result.error("申请记录不存在");
            }
            
            // 验证申请是否属于当前用户
            if (!application.getUserId().equals(userId)) {
                return Result.error("无权查看此申请");
            }
            
            return Result.success(application);
            
        } catch (Exception e) {
            log.error("获取认证申请详情失败", e);
            return Result.error("获取申请详情失败");
        }
    }
    
    /**
     * 检查用户认证状态
     * @param token JWT令牌
     * @return 认证状态信息
     */
    public Result checkUserShelterStatus(String token) {
        try {
            // 解析token获取用户ID
            Map<String, String> tokenInfo = JWTUtils.getTokenInfo(token);
            String userIdStr = tokenInfo.get("userId");
            Integer userId = Integer.parseInt(userIdStr);
            
            log.info("检查用户{}的流浪所认证状态", userId);
            
            // 检查是否已经是流浪所账号
            int shelterCount = shelterAuthMapper.countShelterByUserId(userId);
            if (shelterCount > 0) {
                Map<String, Object> data = new HashMap<>();
                data.put("status", "已认证");
                data.put("message", "您已经是认证的流浪所账号");
                return Result.success(data);
            }
            
            // 检查是否有待审核的申请
            int pendingCount = shelterAuthMapper.countPendingApplicationsByUserId(userId);
            if (pendingCount > 0) {
                Map<String, Object> data = new HashMap<>();
                data.put("status", "审核中");
                data.put("message", "您的认证申请正在审核中，请耐心等待");
                return Result.success(data);
            }
            
            // 获取最近的申请记录
            List<ShelterAuthResponse> applications = shelterAuthMapper.getShelterAuthApplicationsByUserId(userId);
            if (!applications.isEmpty()) {
                ShelterAuthResponse latestApplication = applications.get(0);
                if (latestApplication.getStatus() == 2) {
                    Map<String, Object> data = new HashMap<>();
                    data.put("status", "审核不通过");
                    data.put("message", "您的认证申请未通过审核，可重新申请。原因：" + latestApplication.getAuditReason());
                    return Result.success(data);
                }
            }
            
            Map<String, Object> data = new HashMap<>();
            data.put("status", "未申请");
            data.put("message", "您尚未申请流浪所认证");
            return Result.success(data);
            
        } catch (Exception e) {
            log.error("检查用户流浪所认证状态失败", e);
            return Result.error("检查认证状态失败");
        }
    }
    
    /**
     * 获取所有待审核的认证申请（管理员用）
     * @param token JWT令牌
     * @return 待审核申请列表
     */
    public Result getAllPendingApplications(String token) {
        try {
            // 解析token获取用户信息
            Map<String, String> tokenInfo = JWTUtils.getTokenInfo(token);
            String roleId = tokenInfo.get("roleId");
            
            // 检查管理员权限
            if (!"1".equals(roleId)) {
                return Result.error("权限不足，只有管理员可以查看待审核申请");
            }
            
            log.info("管理员获取所有待审核的流浪所认证申请");
            
            List<ShelterAuthResponse> applications = shelterAuthMapper.getAllPendingApplications();
            
            log.info("共有{}条待审核的认证申请", applications.size());
            return Result.success(applications);
            
        } catch (Exception e) {
            log.error("获取待审核申请列表失败", e);
            return Result.error("获取待审核申请失败");
        }
    }
    
    /**
     * 管理员审核认证申请
     * @param token JWT令牌
     * @param applicationId 申请ID
     * @param status 审核状态 (1-通过, 2-拒绝)
     * @param auditReason 审核原因
     * @return 审核结果
     */
    @Transactional
    public Result auditShelterAuthApplication(String token, Integer applicationId, Integer status, String auditReason) {
        try {
            // 解析token获取用户信息
            Map<String, String> tokenInfo = JWTUtils.getTokenInfo(token);
            String roleId = tokenInfo.get("roleId");
            String adminUserId = tokenInfo.get("userId");
            
            // 检查管理员权限
            if (!"1".equals(roleId)) {
                return Result.error("权限不足，只有管理员可以审核申请");
            }
            
            // 验证状态参数
            if (status != 1 && status != 2) {
                return Result.error("无效的审核状态");
            }
            
            log.info("管理员{}审核认证申请{}，状态：{}", adminUserId, applicationId, status == 1 ? "通过" : "拒绝");
            
            // 检查申请是否存在
            ShelterAuthResponse application = shelterAuthMapper.getShelterAuthApplicationById(applicationId);
            if (application == null) {
                return Result.error("申请记录不存在");
            }
            
            // 检查申请状态
            if (application.getStatus() != 0) {
                return Result.error("该申请已经被审核过了");
            }
            
            // 更新申请状态
            shelterAuthMapper.updateApplicationStatus(applicationId, status, auditReason);
            
            // 如果审核通过，创建流浪所记录
            if (status == 1) {
                shelterAuthMapper.createShelterFromApplication(applicationId);
                log.info("用户{}的流浪所认证申请审核通过，已创建流浪所记录", application.getUserId());
            }
            
            String statusStr = status == 1 ? "通过" : "拒绝";
            return Result.success("审核" + statusStr + "成功");
            
        } catch (Exception e) {
            log.error("审核认证申请失败", e);
            return Result.error("审核申请失败");
        }
    }
}