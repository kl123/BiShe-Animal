package com.example.animal_shelet.pojo.Comment;

import com.example.animal_shelet.config.StringListTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.type.JdbcType;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForumPosts {

  private Integer id;
  private Integer userId;
  private String title;
  private String content;
  private Integer views;
  private Integer likes;
  private Integer status;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  
  // 使用自定义TypeHandler处理数据库字符串到List<String>的映射
  private List<String> imageUrlList;



}
