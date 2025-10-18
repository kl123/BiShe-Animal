package com.example.animal_shelet.contoller.pc;

import com.example.animal_shelet.pojo.result.Result;
import com.example.animal_shelet.service.StatisticsService;
import com.example.animal_shelet.utils.jwt.JWTUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 平台统计数据控制器/pc
 */
@RestController("pcStatisticsController")
@RequestMapping("/statistics")
public class StatisticsController {
    
    @Autowired
    private StatisticsService statisticsService;
    
    /**
     * 获取平台统计数据
     * @param httpServletRequest HTTP请求对象
     * @return 包含平台统计数据的结果
     */
    @GetMapping("/platform")
    public Result getPlatformStatistics(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("token");
        
        // 验证token是否存在
        if (token == null || token.isEmpty()) {
            return Result.error("未提供认证令牌");
        }
        
        try {
            // 验证token并获取用户信息
            Map<String, String> tokenInfo = JWTUtils.getTokenInfo(token);
            String roleId = tokenInfo.get("roleId");
            
            // 检查用户权限（只有管理员可以查看统计数据）
            if (!"1".equals(roleId)) {
                return Result.error("权限不足，只有管理员可以查看统计数据");
            }
            
            // 获取统计数据
            return statisticsService.getPlatformStatistics();
            
        } catch (Exception e) {
            return Result.error("认证失败: " + e.getMessage());
        }
    }
}