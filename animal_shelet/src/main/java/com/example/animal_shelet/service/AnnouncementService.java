package com.example.animal_shelet.service;

import com.example.animal_shelet.mapper.AnnouncementMapper;
import com.example.animal_shelet.pojo.Announcement.Announcement;
import com.example.animal_shelet.pojo.result.Result;
import com.example.animal_shelet.utils.jwt.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 公告服务层
 */
@Service
public class AnnouncementService {
    
    @Autowired
    private AnnouncementMapper announcementMapper;
    
    /**
     * 最大上架公告数量
     */
    private static final int MAX_ACTIVE_ANNOUNCEMENTS = 3;
    
    /**
     * 新增公告
     * @param announcement 公告对象
     * @param token JWT令牌
     * @return 操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public Result addAnnouncement(Announcement announcement, String token) {
        try {
            // 验证token并获取用户信息
            Map<String, String> tokenInfo = JWTUtils.getTokenInfo(token);
            String roleId = tokenInfo.get("roleId");
            String userId = tokenInfo.get("userId");
            
            // 检查用户权限（只有管理员可以操作公告）
            if (!"1".equals(roleId)) {
                return Result.error("权限不足，只有管理员可以操作公告");
            }
            
            // 设置创建者ID
            announcement.setCreatorId(Integer.parseInt(userId));
            
            // 如果要上架新公告，检查当前上架数量
            if (announcement.getStatus() != null && announcement.getStatus() == 1) {
                Integer activeCount = announcementMapper.countActiveAnnouncements();
                if (activeCount >= MAX_ACTIVE_ANNOUNCEMENTS) {
                    return Result.error("最多只能同时上架" + MAX_ACTIVE_ANNOUNCEMENTS + "条公告，请先下架其他公告");
                }
            }
            
            // 设置默认状态为下架
            if (announcement.getStatus() == null) {
                announcement.setStatus(0);
            }
            
            announcementMapper.insertAnnouncement(announcement);
            return Result.success("公告添加成功");
            
        } catch (Exception e) {
            return Result.error("添加公告失败: " + e.getMessage());
        }
    }
    
    /**
     * 删除公告
     * @param id 公告ID
     * @param token JWT令牌
     * @return 操作结果
     */
    public Result deleteAnnouncement(Integer id, String token) {
        try {
            // 验证token并获取用户信息
            Map<String, String> tokenInfo = JWTUtils.getTokenInfo(token);
            String roleId = tokenInfo.get("roleId");
            
            // 检查用户权限
            if (!"1".equals(roleId)) {
                return Result.error("权限不足，只有管理员可以操作公告");
            }
            
            // 检查公告是否存在
            Announcement announcement = announcementMapper.selectAnnouncementById(id);
            if (announcement == null) {
                return Result.error("公告不存在");
            }
            
            announcementMapper.deleteAnnouncementById(id);
            return Result.success("公告删除成功");
            
        } catch (Exception e) {
            return Result.error("删除公告失败: " + e.getMessage());
        }
    }
    
    /**
     * 更新公告
     * @param announcement 公告对象
     * @param token JWT令牌
     * @return 操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public Result updateAnnouncement(Announcement announcement, String token) {
        try {
            // 验证token并获取用户信息
            Map<String, String> tokenInfo = JWTUtils.getTokenInfo(token);
            String roleId = tokenInfo.get("roleId");
            
            // 检查用户权限
            if (!"1".equals(roleId)) {
                return Result.error("权限不足，只有管理员可以操作公告");
            }
            
            // 检查公告是否存在
            Announcement existingAnnouncement = announcementMapper.selectAnnouncementById(announcement.getId());
            if (existingAnnouncement == null) {
                return Result.error("公告不存在");
            }
            
            // 如果要上架公告，检查当前上架数量
            if (announcement.getStatus() != null && announcement.getStatus() == 1 
                && existingAnnouncement.getStatus() == 0) {
                Integer activeCount = announcementMapper.countActiveAnnouncements();
                if (activeCount >= MAX_ACTIVE_ANNOUNCEMENTS) {
                    return Result.error("最多只能同时上架" + MAX_ACTIVE_ANNOUNCEMENTS + "条公告，请先下架其他公告");
                }
            }
            
            announcementMapper.updateAnnouncement(announcement);
            return Result.success("公告更新成功");
            
        } catch (Exception e) {
            return Result.error("更新公告失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据ID查询公告
     * @param id 公告ID
     * @return 公告对象
     */
    public Result getAnnouncementById(Integer id) {
        try {
            Announcement announcement = announcementMapper.selectAnnouncementById(id);
            if (announcement == null) {
                return Result.error("公告不存在");
            }
            return Result.success(announcement);
        } catch (Exception e) {
            return Result.error("查询公告失败: " + e.getMessage());
        }
    }
    
    /**
     * 查询所有公告（管理员用）
     * @param token JWT令牌
     * @return 公告列表
     */
    public Result getAllAnnouncements(String token) {
        try {
            // 验证token并获取用户信息
            Map<String, String> tokenInfo = JWTUtils.getTokenInfo(token);
            String roleId = tokenInfo.get("roleId");
            
            // 检查用户权限
            if (!"1".equals(roleId)) {
                return Result.error("权限不足，只有管理员可以查看所有公告");
            }
            
            List<Announcement> announcements = announcementMapper.selectAllAnnouncements();
            return Result.success(announcements);
            
        } catch (Exception e) {
            return Result.error("查询公告列表失败: " + e.getMessage());
        }
    }
    
    /**
     * 查询上架的公告（公开接口）
     * @return 上架的公告列表
     */
    public Result getActiveAnnouncements() {
        try {
            List<Announcement> announcements = announcementMapper.selectActiveAnnouncements();
            return Result.success(announcements);
        } catch (Exception e) {
            return Result.error("查询公告失败: " + e.getMessage());
        }
    }
    
    /**
     * 批量上架/下架公告
     * @param ids 公告ID列表
     * @param status 状态（0:下架, 1:上架）
     * @param token JWT令牌
     * @return 操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public Result batchUpdateAnnouncementStatus(List<Integer> ids, Integer status, String token) {
        try {
            // 验证token并获取用户信息
            Map<String, String> tokenInfo = JWTUtils.getTokenInfo(token);
            String roleId = tokenInfo.get("roleId");
            
            // 检查用户权限
            if (!"1".equals(roleId)) {
                return Result.error("权限不足，只有管理员可以操作公告");
            }
            
            // 如果是批量上架，检查数量限制
            if (status == 1) {
                Integer currentActiveCount = announcementMapper.countActiveAnnouncements();
                // 计算需要上架的公告中当前未上架的数量
                List<Announcement> toUpdateAnnouncements = new ArrayList<>();
                for (Integer id : ids) {
                    Announcement announcement = announcementMapper.selectAnnouncementById(id);
                    if (announcement != null && announcement.getStatus() == 0) {
                        toUpdateAnnouncements.add(announcement);
                    }
                }
                
                if (currentActiveCount + toUpdateAnnouncements.size() > MAX_ACTIVE_ANNOUNCEMENTS) {
                    return Result.error("批量上架后将超过最大上架数量限制（" + MAX_ACTIVE_ANNOUNCEMENTS + "条）");
                }
            }
            
            announcementMapper.batchUpdateAnnouncementStatus(ids, status);
            String operation = status == 1 ? "上架" : "下架";
            return Result.success("批量" + operation + "成功");
            
        } catch (Exception e) {
            return Result.error("批量操作失败: " + e.getMessage());
        }
    }
}