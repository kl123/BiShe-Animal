package com.example.animal_shelet.mapper;

import com.example.animal_shelet.pojo.Comment.ForumComments;
import com.example.animal_shelet.pojo.Comment.ForumPosts;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentsMapper {
    void insertForumPost(ForumPosts forumPosts);

    List<ForumPosts> getForumPosts();

    void insertForumComments(ForumComments forumComments);
}
