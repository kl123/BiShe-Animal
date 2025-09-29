package com.example.animal_shelet.contoller.UserController;

import com.example.animal_shelet.pojo.User.User;
import com.example.animal_shelet.pojo.result.Result;
import com.example.animal_shelet.service.UserService;
import com.example.animal_shelet.utils.AliOSSUtils;
import com.example.animal_shelet.utils.jwt.JWTUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 用户接口控制(pubulc)
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class getAllUsers{
        private int page;
        private int limit;
        private User user;
    }

    /**
     * 登录接口
     * @param loginData
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody Map<String, Object> loginData){

        String username = (String) loginData.get("username");
        String password = (String) loginData.get("password");
        return userService.checklogin(username,password);
    }

    /**
     * 分页查询用户,传参数当前页面,每页数目
     * @param Data
     * @return
     */
    @PostMapping("/getusers")
    public Result getAllUsers(@RequestBody getAllUsers Data, HttpServletRequest httpServletRequest){
        int page = Data.getPage();
        int limit = Data.getLimit();
        User user = Data.getUser();
        String token = httpServletRequest.getHeader("token");
        Map<String,String> map = JWTUtils.getTokenInfo(token);
        return userService.getAllUsers(page,limit,map,user);
    }

    /**
     * 注册接口
     * @param registerData
     * @return
     */
    @PostMapping("/register")
    public Result register(@RequestBody Map<String, Object> registerData){
        // 1. 提取前端传来的数据
        String username = (String) registerData.get("username");
        String phone = (String) registerData.get("phone");
        String password = (String) registerData.get("password");
        String email = (String) registerData.get("email");
        return userService.register(username,phone,password,email);
    }


    /**
     * 上传图片
     * @param file
     * @return
     */
    @PostMapping("/uploadImage")
    public Result uploadImage(@RequestParam("file") MultipartFile file) {
        // 1. 验证文件是否存在
        if (file.isEmpty()) {
            return Result.error("文件不能为空");
        }

        try {
            AliOSSUtils ossUtils = new AliOSSUtils();
            String url = ossUtils.upload(file);
            return Result.success(url);

        } catch (IOException e) {
            return Result.error("文件读取失败: " + e.getMessage());
        } catch (Exception e) {
            return Result.error("上传异常: " + e.getMessage());
        }
    }

    /**
     * 删除用户
     * @param deleteData
     * @return
     */
    @DeleteMapping("/deleteUser")
    public Result deleteUser(@RequestBody Map<String,String> deleteData){
        String Id = deleteData.get("id");
        return userService.deleteUser(Id);
    }

    /**
     * 批量删除用户
     * @param deleteData
     * @return
     */
    @DeleteMapping("/deleteUserList")
    public Result deleteUserList(@RequestBody Map<String,List<String>> deleteData){
        List<String> IdList = deleteData.get("userIdList");
        return userService.deleteUserList(IdList);
    }

}
