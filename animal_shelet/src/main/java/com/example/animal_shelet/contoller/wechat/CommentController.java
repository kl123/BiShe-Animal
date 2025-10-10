package com.example.animal_shelet.contoller.wechat;

import com.example.animal_shelet.pojo.Comment.ForumComments;
import com.example.animal_shelet.pojo.Comment.ForumPosts;
import com.example.animal_shelet.pojo.result.Result;
import com.example.animal_shelet.service.CommentsService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("CommentController")
@RequestMapping("/wechat/Comment")
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


}
