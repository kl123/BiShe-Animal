package com.example.animal_shelet.service;

import com.example.animal_shelet.pojo.User.User;
import com.example.animal_shelet.mapper.Usermapper;
import com.example.animal_shelet.pojo.User.PageUser;
import com.example.animal_shelet.pojo.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private Usermapper userMapper;

    public Result getAllUsers(int page, int limit){
        int offset = (page - 1) * limit;
        int total = userMapper.selectAll().size();
        List<User> users = userMapper.getPageuser(offset,limit);
        System.out.println(users);
//        System.out.println("总数目为:"+total);
        PageUser users_page = new PageUser(users,total);
//        userMapper.getPageuser(page,limit);

        return Result.success(users_page);
    }

    public Result checklogin(String username, String password) {
        //直接查表
        List<User> user = userMapper.checklogin(username,password);
        if (user.size()>0){
            //成功查询到用户,可以进行登录
            int identity = user.get(0).getRoleId();
            int userid = user.get(0).getId();
            String identity_str = "";
            switch (identity){
                case 1:
                    identity_str = "管理员";
                    break;
                case 2:
                    identity_str = "普通用户";
                    break;
                default:
                    System.out.println("错误...");
                    System.out.println( identity);
                    System.out.println(user);
                    break;
            }
            Result result = Result.success();
            result.setData(userid);
            result.setMsg(identity_str);
            return result;
        }else {
            Result result = Result.error("登录失败,用户名或密码错误");
            return result;
        }

    }

    public Result register(String username, String phone, String password, String email) {
        //判断手机号是否注册过,判断用户名是否重复
        int result = userMapper.countByUsername(username);
        int result1 = userMapper.countByPhone(phone);
        if (result>0){
            return Result.error("用户名已存在");
        }else if (result1>0){
            return Result.error("该手机号已注册过");
        }

        //进行注册
        userMapper.register(username,phone,password,email);
        //这边执行注册成功的逻辑
        return Result.success("注册成功");
    }
}
