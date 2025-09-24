package com.example.animal_shelet.contoller.pc;

import com.example.animal_shelet.pojo.result.Result;
import com.example.animal_shelet.service.AuditService;
import com.example.animal_shelet.utils.jwt.JWTUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 审核控制/pc
 */
@RestController("PcAuditController")
@RequestMapping("/pc")
public class AuditController {
    @Autowired
    private AuditService auditService;

    @GetMapping("/getAuditRecords")
    public Result getAuditRecords(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");
        Map<String,String> map = JWTUtils.getTokenInfo(token);
        String userId = map.get("userId");
        return auditService.getAuditRecords(userId);
    }
}
