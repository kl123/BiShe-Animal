package com.example.animal_shelet.mapper;

import com.example.animal_shelet.pojo.Comment.And.ForumCommentsAndUser;
import com.example.animal_shelet.pojo.Comment.ForumComments;
import com.example.animal_shelet.pojo.Comment.ForumPosts;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentsMapper {
    void insertForumPost(@Param("forumPosts") ForumPosts forumPosts, @Param("imageUrlJson") String imageUrlJson);

    List<ForumPosts> getForumPosts();

    void insertForumComments(ForumComments forumComments);

    void AuditPost(String postId);

    void likePost(Integer postId);

    void unlikePost(Integer postId);

    List<ForumCommentsAndUser> getCommentByPostId(String postId);
}
