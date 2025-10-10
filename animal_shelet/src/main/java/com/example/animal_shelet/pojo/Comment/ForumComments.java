package com.example.animal_shelet.pojo.Comment;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.internal.build.AllowNonPortable;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllowNonPortable
public class ForumComments {

  private Integer id;
  private Integer postId;
  private Integer userId;
  private String content;
  private Integer likes;
  private Integer status;
  private LocalDateTime createdAt;
  private String imageUrl;
  
}
