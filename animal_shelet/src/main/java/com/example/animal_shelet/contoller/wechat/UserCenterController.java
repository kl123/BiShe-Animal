package com.example.animal_shelet.contoller.wechat;

import com.example.animal_shelet.pojo.result.Result;
import com.example.animal_shelet.service.UserCenterService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微信端用户中心控制器
 */
@Slf4j
@RestController("wechatUserCenterController")
@RequestMapping("/wechat/user")
public class UserCenterController {
    
    @Autowired
    private UserCenterService userCenterService;
    
    /**
     * 获取用户个人中心信息
     * 包含：用户名称、用户领养宠物数目、用户是否为流浪所账号、
     * 流浪所账号已售出动物数、流浪所账号正在售出动物数、发布帖子数
     * 
     * @param httpServletRequest HTTP请求对象
     * @return 用户个人中心信息
     */
    @GetMapping("/profile")
    public Result getUserProfile(HttpServletRequest httpServletRequest) {
        try {
            // 从请求头获取token
            String token = httpServletRequest.getHeader("token");
            
            if (token == null || token.trim().isEmpty()) {
                log.warn("获取用户个人中心信息失败：token为空");
                return Result.error("请先登录");
            }
            
            log.info("获取用户个人中心信息请求，token: {}", token.substring(0, Math.min(token.length(), 20)) + "...");
            
            // 调用服务层获取用户个人中心信息
            return userCenterService.getUserProfile(token);
            
        } catch (Exception e) {
            log.error("获取用户个人中心信息异常", e);
            return Result.error("系统异常，请稍后重试");
        }
    }
}