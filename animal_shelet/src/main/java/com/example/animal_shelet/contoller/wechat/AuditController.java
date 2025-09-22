package com.example.animal_shelet.contoller.wechat;

import com.example.animal_shelet.pojo.result.Result;
import com.example.animal_shelet.service.AuditService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 审核控制/wechat
 */
@RestController("WechatAuditController")
@RequestMapping("/wechat")
public class AuditController {
    @Autowired
    private AuditService auditService;

    /**
     * 获取通过的申请信息
     * @param httpServletRequest
     * @return
     */
    @GetMapping("/getApplicationInformation")
    public Result getApplicationInformation(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("token");
        return auditService.getApplicationInformation(token);
    }
}
