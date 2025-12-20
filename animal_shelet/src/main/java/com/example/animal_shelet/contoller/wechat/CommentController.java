package com.example.animal_shelet.contoller.wechat;

import com.example.animal_shelet.pojo.Comment.ForumComments;
import com.example.animal_shelet.pojo.Comment.ForumPosts;
import com.example.animal_shelet.pojo.result.Result;
import com.example.animal_shelet.service.CommentsService;
import com.example.animal_shelet.utils.jwt.JWTUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController("CommentControllerWechat")
@RequestMapping("/wechat/Comment")
@Slf4j
public class CommentController {
    /**
     * 帖子服务控制/wechat
     */
    @Autowired
    private CommentsService commentsService;

    /**
     * 发布帖子
     * @param forumPosts
     * @param httpServletRequest
     * @return
     */
    @PostMapping("/PublishPost")
    public Result PublishPost(@RequestBody ForumPosts forumPosts, HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");
        return commentsService.PublishPost(forumPosts,token);
    }

    /**
     * 获取帖子
     * @param httpServletRequest
     * @return
     */
    @GetMapping("/getPosts")
    public Result getPosts(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");
        return commentsService.getPosts(token);
    }


    /**
     * 发布评论
     * @param forumComments
     * @param httpServletRequest
     * @return
     */
    @PostMapping("/PublishReview")
    public Result PublishReview(@RequestBody ForumComments forumComments, HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");
        return commentsService.PublishReview(forumComments,token);
    }
    /**
     * 获取评论
     * @param result
     * @param httpServletRequest
     * @return
     */
    @PostMapping("/getCommentByPostId")
    public Result getCommentByPostId(@RequestBody Map<String,Object> result, HttpServletRequest httpServletRequest){
        String postId = String.valueOf(result.get("postId"));
        String token = httpServletRequest.getHeader("token");
        return commentsService.getCommentByPostId(postId,token);
    }

    /**
     * 点赞帖子
     * @param result
     * @return
     */
    @PostMapping("/likePost")
    public Result likePost(@RequestBody Map<String,Object> result){
        Integer postId = (Integer) result.get("postId");
        return commentsService.likePost(postId);
    }

    /**
     * 取消点赞帖子
     * @param result
     * @return
     */
    @PostMapping("/unlikePost")
    public Result unlikePost(@RequestBody Map<String,Object> result){
        Integer postId = (Integer) result.get("postId");
        return commentsService.unlikePost(postId);
    }

}
