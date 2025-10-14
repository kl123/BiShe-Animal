package com.example.animal_shelet.contoller.pc;

import com.example.animal_shelet.pojo.Animal.AuditRecords;
import com.example.animal_shelet.pojo.result.Result;
import com.example.animal_shelet.service.AuditService;
import com.example.animal_shelet.service.CommentsService;
import com.example.animal_shelet.utils.jwt.JWTUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController("CommentControllerPc")
@RequestMapping("/pc/Comment")
public class CommentController {
    /**
     * 帖子服务控制/pc
     */
    @Autowired
    private CommentsService commentsService;

    @Autowired
    private AuditService auditService;
    /**
     * 审核帖子
     * @param result
     * @param httpServletRequest
     * @return
     */
    @PostMapping("/AuditPost")
    public Result AuditPost(@RequestBody Map<String,Object> result, HttpServletRequest httpServletRequest){
        String postId = String.valueOf(result.get("postId"));
        String targetId = String.valueOf(result.get("targetId"));
        String reason = String.valueOf(result.get("reason"));
    //        String action = result.get("action").toString();
        String token = httpServletRequest.getHeader("token");
        Map<String,String> tokenInfo = JWTUtils.getTokenInfo(token);
        String roleId = tokenInfo.get("roleId");
        String userId =  tokenInfo.get("userId");
        String targetType = "2";
        String action = "1";
        if(roleId.equals("1")){
            commentsService.AuditPost(postId);
            auditService.AuditRecordsAdd(targetId,userId,reason,targetType,action);
            return Result.success();
        }else{
            return Result.error("权限不足");
        }
    }


}
