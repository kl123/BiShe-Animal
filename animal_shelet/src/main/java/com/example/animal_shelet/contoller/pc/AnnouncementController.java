package com.example.animal_shelet.contoller.pc;

import com.example.animal_shelet.pojo.Announcement.Announcement;
import com.example.animal_shelet.pojo.result.Result;
import com.example.animal_shelet.service.AnnouncementService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 公告控制器/pc
 */
@RestController("pcAnnouncementController")
@RequestMapping("/announcement")
@Slf4j
public class AnnouncementController {
    
    @Autowired
    private AnnouncementService announcementService;
    
    /**
     * 新增公告
     * @param announcement 公告对象
     * @param httpServletRequest HTTP请求对象
     * @return 操作结果
     */
    @PostMapping("/add")
    public Result addAnnouncement(@RequestBody Announcement announcement, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("token");
        
        if (token == null || token.isEmpty()) {
            return Result.error("未提供认证令牌");
        }
        
        // 验证必填字段
        if (announcement.getName() == null || announcement.getName().trim().isEmpty()) {
            return Result.error("公告名称不能为空");
        }
        if (announcement.getContent() == null || announcement.getContent().trim().isEmpty()) {
            return Result.error("公告内容不能为空");
        }
        
        return announcementService.addAnnouncement(announcement, token);
    }
    
    /**
     * 删除公告
     * @param requestBody 请求体，包含公告ID
     * @param httpServletRequest HTTP请求对象
     * @return 操作结果
     */
    @PostMapping("/delete")
    public Result deleteAnnouncement(@RequestBody Map<String, Object> requestBody, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("token");
        
        if (token == null || token.isEmpty()) {
            return Result.error("未提供认证令牌");
        }
        
        Object idObj = requestBody.get("id");
        if (idObj == null) {
            return Result.error("公告ID不能为空");
        }
        
        Integer id = Integer.valueOf(idObj.toString());
        return announcementService.deleteAnnouncement(id, token);
    }
    
    /**
     * 更新公告
     * @param announcement 公告对象
     * @param httpServletRequest HTTP请求对象
     * @return 操作结果
     */
    @PostMapping("/update")
    public Result updateAnnouncement(@RequestBody Announcement announcement, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("token");
        
        if (token == null || token.isEmpty()) {
            return Result.error("未提供认证令牌");
        }
        
        // 验证必填字段
        if (announcement.getId() == null) {
            return Result.error("公告ID不能为空");
        }
        if (announcement.getName() == null || announcement.getName().trim().isEmpty()) {
            return Result.error("公告名称不能为空");
        }
        if (announcement.getContent() == null || announcement.getContent().trim().isEmpty()) {
            return Result.error("公告内容不能为空");
        }
        
        return announcementService.updateAnnouncement(announcement, token);
    }
    
    /**
     * 根据ID查询公告
     * @param id 公告ID
     * @return 公告对象
     */
    @GetMapping("/get/{id}")
    public Result getAnnouncementById(@PathVariable Integer id) {
        if (id == null) {
            return Result.error("公告ID不能为空");
        }
        
        return announcementService.getAnnouncementById(id);
    }
    
    /**
     * 查询所有公告（管理员用）
     * @param httpServletRequest HTTP请求对象
     * @return 公告列表
     */
    @GetMapping("/admin/list")
    public Result getAllAnnouncements(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("token");
        
        if (token == null || token.isEmpty()) {
            return Result.error("未提供认证令牌");
        }
        
        return announcementService.getAllAnnouncements(token);
    }
    
    /**
     * 查询上架的公告（公开接口）
     * @return 上架的公告列表
     */
    @GetMapping("/active")
    public Result getActiveAnnouncements() {
        return announcementService.getActiveAnnouncements();
    }
    
    /**
     * 批量上架/下架公告
     * @param requestBody 请求体，包含公告ID列表和状态
     * @param httpServletRequest HTTP请求对象
     * @return 操作结果
     */
    @PostMapping("/batch/status")
    public Result batchUpdateAnnouncementStatus(@RequestBody Map<String, Object> requestBody, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("token");
        
        if (token == null || token.isEmpty()) {
            return Result.error("未提供认证令牌");
        }
        
        Object idsObj = requestBody.get("ids");
        Object statusObj = requestBody.get("status");
        
        if (idsObj == null || statusObj == null) {
            return Result.error("公告ID列表和状态不能为空");
        }
        
        try {
            @SuppressWarnings("unchecked")
            List<Integer> ids = (List<Integer>) idsObj;
            Integer status = Integer.valueOf(statusObj.toString());
            
            if (ids.isEmpty()) {
                return Result.error("公告ID列表不能为空");
            }
            
            if (status != 0 && status != 1) {
                return Result.error("状态值只能为0（下架）或1（上架）");
            }
            
            return announcementService.batchUpdateAnnouncementStatus(ids, status, token);
            
        } catch (Exception e) {
            return Result.error("参数格式错误: " + e.getMessage());
        }
    }
}