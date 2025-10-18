package com.example.animal_shelet.contoller.wechat;

import com.example.animal_shelet.pojo.ShelterAuth.ShelterAuthRequest;
import com.example.animal_shelet.pojo.result.Result;
import com.example.animal_shelet.service.ShelterAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 微信端流浪所认证控制器
 */
@Slf4j
@RestController("wechatShelterAuthController")
@RequestMapping("/wechat/shelter-auth")
public class ShelterAuthController {
    
    @Autowired
    private ShelterAuthService shelterAuthService;
    
    /**
     * 提交流浪所认证申请
     * 
     * @param request 认证申请信息
     * @param httpServletRequest HTTP请求对象
     * @return 申请结果
     */
    @PostMapping("/apply")
    public Result submitApplication(@RequestBody ShelterAuthRequest request, 
                                   HttpServletRequest httpServletRequest) {
        try {
            // 从请求头获取token
            String token = httpServletRequest.getHeader("token");
            
            if (token == null || token.trim().isEmpty()) {
                log.warn("提交流浪所认证申请失败：token为空");
                return Result.error("请先登录");
            }
            
            log.info("收到流浪所认证申请，流浪所名称: {}", request.getShelterName());
            
            // 调用服务层提交申请
            return shelterAuthService.submitShelterAuthApplication(token, request);
            
        } catch (Exception e) {
            log.error("提交流浪所认证申请异常", e);
            return Result.error("系统异常，请稍后重试");
        }
    }
    
    /**
     * 获取用户的认证申请记录
     * 
     * @param httpServletRequest HTTP请求对象
     * @return 申请记录列表
     */
    @GetMapping("/applications")
    public Result getUserApplications(HttpServletRequest httpServletRequest) {
        try {
            // 从请求头获取token
            String token = httpServletRequest.getHeader("token");
            
            if (token == null || token.trim().isEmpty()) {
                log.warn("获取认证申请记录失败：token为空");
                return Result.error("请先登录");
            }
            
            log.info("获取用户认证申请记录");
            
            // 调用服务层获取申请记录
            return shelterAuthService.getUserShelterAuthApplications(token);
            
        } catch (Exception e) {
            log.error("获取用户认证申请记录异常", e);
            return Result.error("系统异常，请稍后重试");
        }
    }
    
    /**
     * 获取认证申请详情
     * 
     * @param applicationId 申请ID
     * @param httpServletRequest HTTP请求对象
     * @return 申请详情
     */
    @GetMapping("/applications/{applicationId}")
    public Result getApplicationDetail(@PathVariable Integer applicationId, 
                                     HttpServletRequest httpServletRequest) {
        try {
            // 从请求头获取token
            String token = httpServletRequest.getHeader("token");
            
            if (token == null || token.trim().isEmpty()) {
                log.warn("获取认证申请详情失败：token为空");
                return Result.error("请先登录");
            }
            
            log.info("获取认证申请详情，申请ID: {}", applicationId);
            
            // 调用服务层获取申请详情
            return shelterAuthService.getShelterAuthApplicationDetail(token, applicationId);
            
        } catch (Exception e) {
            log.error("获取认证申请详情异常", e);
            return Result.error("系统异常，请稍后重试");
        }
    }
    
    /**
     * 检查用户流浪所认证状态
     * 
     * @param httpServletRequest HTTP请求对象
     * @return 认证状态信息
     */
    @GetMapping("/status")
    public Result checkAuthStatus(HttpServletRequest httpServletRequest) {
        try {
            // 从请求头获取token
            String token = httpServletRequest.getHeader("token");
            
            if (token == null || token.trim().isEmpty()) {
                log.warn("检查认证状态失败：token为空");
                return Result.error("请先登录");
            }
            
            log.info("检查用户流浪所认证状态");
            
            // 调用服务层检查认证状态
            return shelterAuthService.checkUserShelterStatus(token);
            
        } catch (Exception e) {
            log.error("检查用户流浪所认证状态异常", e);
            return Result.error("系统异常，请稍后重试");
        }
    }
}