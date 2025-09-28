package com.example.animal_shelet.contoller.pc;

import com.example.animal_shelet.pojo.result.Result;
import com.example.animal_shelet.service.AuditService;
import com.example.animal_shelet.utils.jwt.JWTUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 审核控制/pc
 */
@RestController("pcAuditController")
@Slf4j
@RequestMapping("/pc/audit")
public class AuditController {
    @Autowired
    private AuditService auditService;

    /**
     * 展示审批记录
     * @param httpServletRequest
     * @return
     */
    @GetMapping("/getAllAuditInfoList")
    public Result getAllAuditInfoList(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("token");
        Map<String,String> tokenInfo = JWTUtils.getTokenInfo(token);
        return auditService.getAllAuditInfoList(tokenInfo);
    }

}
