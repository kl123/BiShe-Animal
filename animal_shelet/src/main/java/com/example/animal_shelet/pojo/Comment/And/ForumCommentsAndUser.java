package com.example.animal_shelet.pojo.Comment.And;

import com.example.animal_shelet.pojo.Comment.ForumComments;
import com.example.animal_shelet.pojo.User.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForumCommentsAndUser {
    private Integer id;
    private Integer postId;
    private Integer userId;
    private String content;
    private Integer likes;
    private Integer status;
    private LocalDateTime createdAt;
    private String imageUrl;
    private String username;
}
