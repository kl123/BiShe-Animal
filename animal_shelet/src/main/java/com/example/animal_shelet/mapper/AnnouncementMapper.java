package com.example.animal_shelet.mapper;

import com.example.animal_shelet.pojo.Announcement.Announcement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 公告Mapper接口
 */
@Mapper
public interface AnnouncementMapper {
    
    /**
     * 新增公告
     * @param announcement 公告对象
     */
    void insertAnnouncement(Announcement announcement);
    
    /**
     * 根据ID删除公告
     * @param id 公告ID
     */
    void deleteAnnouncementById(@Param("id") Integer id);
    
    /**
     * 更新公告
     * @param announcement 公告对象
     */
    void updateAnnouncement(Announcement announcement);
    
    /**
     * 根据ID查询公告
     * @param id 公告ID
     * @return 公告对象
     */
    Announcement selectAnnouncementById(@Param("id") Integer id);
    
    /**
     * 查询所有公告（按创建时间倒序）
     * @return 公告列表
     */
    List<Announcement> selectAllAnnouncements();
    
    /**
     * 查询上架的公告（按创建时间倒序）
     * @return 上架的公告列表
     */
    List<Announcement> selectActiveAnnouncements();
    
    /**
     * 统计上架的公告数量
     * @return 上架公告数量
     */
    Integer countActiveAnnouncements();
    
    /**
     * 批量下架公告
     * @param ids 公告ID列表
     */
    void batchUpdateAnnouncementStatus(@Param("ids") List<Integer> ids, @Param("status") Integer status);
}