package com.example.animal_shelet.service;

import com.example.animal_shelet.mapper.CommentsMapper;
import com.example.animal_shelet.pojo.Comment.ForumComments;
import com.example.animal_shelet.pojo.Comment.ForumPosts;
import com.example.animal_shelet.pojo.result.Result;
import com.example.animal_shelet.utils.jwt.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CommentsService {
    @Autowired
    private CommentsMapper commentsMapper;

    public Result PublishPost(ForumPosts forumPosts, String token) {
        Map<String, String> tokenInfo = JWTUtils.getTokenInfo(token);
        String userId = tokenInfo.get("userId");
        forumPosts.setUserId(Integer.parseInt(userId));
        commentsMapper.insertForumPost(forumPosts);
        return Result.success();

    }

    public Result getPosts(String token) {
        List<ForumPosts> forumPosts = commentsMapper.getForumPosts();
        return Result.success(forumPosts);
    }

    public Result PublishReview(ForumComments forumComments, String token) {
        Map<String, String> tokenInfo = JWTUtils.getTokenInfo(token);
        String userId = tokenInfo.get("userId");
        forumComments.setUserId(Integer.parseInt(userId));
        commentsMapper.insertForumComments(forumComments);
        return Result.success();
    }
}
