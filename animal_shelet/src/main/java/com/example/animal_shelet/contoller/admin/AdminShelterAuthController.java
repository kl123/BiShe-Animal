package com.example.animal_shelet.contoller.admin;

import com.example.animal_shelet.pojo.result.Result;
import com.example.animal_shelet.service.ShelterAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 管理员端流浪所认证审核Controller
 */
@Slf4j
@RestController
@RequestMapping("/admin/shelter-auth")
public class AdminShelterAuthController {
    
    @Autowired
    private ShelterAuthService shelterAuthService;
    
    /**
     * 获取所有待审核的认证申请
     * @param request HTTP请求对象
     * @return 待审核申请列表
     */
    @GetMapping("/pending")
    public Result getPendingApplications(HttpServletRequest request) {
        try {
            String token = request.getHeader("Authorization");
            if (token == null || token.isEmpty()) {
                return Result.error("未提供认证令牌");
            }
            
            log.info("管理员获取待审核的流浪所认证申请列表");
            return shelterAuthService.getAllPendingApplications(token);
            
        } catch (Exception e) {
            log.error("获取待审核申请列表失败", e);
            return Result.error("获取申请列表失败");
        }
    }
    
    /**
     * 审核认证申请
     * @param request HTTP请求对象
     * @param applicationId 申请ID
     * @param status 审核状态 (1-通过, 2-拒绝)
     * @param auditReason 审核原因
     * @return 审核结果
     */
    @PostMapping("/audit/{applicationId}")
    public Result auditApplication(
            HttpServletRequest request,
            @PathVariable Integer applicationId,
            @RequestParam Integer status,
            @RequestParam(required = false) String auditReason) {
        try {
            String token = request.getHeader("Authorization");
            if (token == null || token.isEmpty()) {
                return Result.error("未提供认证令牌");
            }
            
            // 验证必填参数
            if (applicationId == null) {
                return Result.error("申请ID不能为空");
            }
            if (status == null) {
                return Result.error("审核状态不能为空");
            }
            
            // 如果是拒绝申请，审核原因必填
            if (status == 2 && (auditReason == null || auditReason.trim().isEmpty())) {
                return Result.error("拒绝申请时必须填写审核原因");
            }
            
            log.info("管理员审核认证申请{}，状态：{}", applicationId, status == 1 ? "通过" : "拒绝");
            return shelterAuthService.auditShelterAuthApplication(token, applicationId, status, auditReason);
            
        } catch (Exception e) {
            log.error("审核认证申请失败", e);
            return Result.error("审核申请失败");
        }
    }
    
    /**
     * 批量审核认证申请
     * @param request HTTP请求对象
     * @param applicationIds 申请ID列表
     * @param status 审核状态 (1-通过, 2-拒绝)
     * @param auditReason 审核原因
     * @return 批量审核结果
     */
    @PostMapping("/batch-audit")
    public Result batchAuditApplications(
            HttpServletRequest request,
            @RequestParam String applicationIds,
            @RequestParam Integer status,
            @RequestParam(required = false) String auditReason) {
        try {
            String token = request.getHeader("Authorization");
            if (token == null || token.isEmpty()) {
                return Result.error("未提供认证令牌");
            }
            
            // 验证必填参数
            if (applicationIds == null || applicationIds.trim().isEmpty()) {
                return Result.error("申请ID列表不能为空");
            }
            if (status == null) {
                return Result.error("审核状态不能为空");
            }
            
            // 如果是拒绝申请，审核原因必填
            if (status == 2 && (auditReason == null || auditReason.trim().isEmpty())) {
                return Result.error("拒绝申请时必须填写审核原因");
            }
            
            // 解析申请ID列表
            String[] idArray = applicationIds.split(",");
            int successCount = 0;
            int failCount = 0;
            StringBuilder errorMessages = new StringBuilder();
            
            for (String idStr : idArray) {
                try {
                    Integer applicationId = Integer.parseInt(idStr.trim());
                    Result result = shelterAuthService.auditShelterAuthApplication(token, applicationId, status, auditReason);
                    if (result.getCode() == 1) {
                        successCount++;
                    } else {
                        failCount++;
                        errorMessages.append("申请").append(applicationId).append("：").append(result.getMsg()).append("; ");
                    }
                } catch (NumberFormatException e) {
                    failCount++;
                    errorMessages.append("无效的申请ID：").append(idStr).append("; ");
                }
            }
            
            String message = String.format("批量审核完成，成功：%d条，失败：%d条", successCount, failCount);
            if (failCount > 0) {
                message += "。失败原因：" + errorMessages.toString();
            }
            
            log.info("批量审核结果：{}", message);
            return Result.success(message);
            
        } catch (Exception e) {
            log.error("批量审核认证申请失败", e);
            return Result.error("批量审核申请失败");
        }
    }
    
    /**
     * 获取申请详情（管理员查看）
     * @param request HTTP请求对象
     * @param applicationId 申请ID
     * @return 申请详情
     */
    @GetMapping("/application/{applicationId}")
    public Result getApplicationDetail(HttpServletRequest request, @PathVariable Integer applicationId) {
        try {
            String token = request.getHeader("Authorization");
            if (token == null || token.isEmpty()) {
                return Result.error("未提供认证令牌");
            }
            
            if (applicationId == null) {
                return Result.error("申请ID不能为空");
            }
            
            log.info("管理员查看认证申请{}详情", applicationId);
            
            // 这里可以直接调用mapper获取详情，因为管理员有权限查看所有申请
            // 为了复用代码，我们可以在Service中添加一个管理员专用的方法
            // 暂时使用现有方法，但需要注意权限验证
            return shelterAuthService.getShelterAuthApplicationDetail(token, applicationId);
            
        } catch (Exception e) {
            log.error("获取认证申请详情失败", e);
            return Result.error("获取申请详情失败");
        }
    }
}