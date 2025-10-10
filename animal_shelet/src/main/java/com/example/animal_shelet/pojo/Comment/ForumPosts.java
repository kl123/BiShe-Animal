package com.example.animal_shelet.pojo.Comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
  private String imageUrl;


}
