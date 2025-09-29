package com.example.animal_shelet.service;

import com.example.animal_shelet.pojo.User.User;
import com.example.animal_shelet.mapper.Usermapper;
import com.example.animal_shelet.pojo.User.PageUser;
import com.example.animal_shelet.pojo.result.Result;
import com.example.animal_shelet.utils.jwt.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private Usermapper userMapper;

    public Result getAllUsers(int page, int limit, Map<String, String> map, User user){
        String roleId = map.get("roleId");
        if (roleId.equals("1")){
            int offset = (page - 1) * limit;
            int total = userMapper.selectAll().size();
            List<User> users;
            if (user == null){
                users = userMapper.getPageUser_UserNull(offset,limit);
            }else{
                users = userMapper.getPageUser_UserNotNUll(offset,limit,user);
            }
            PageUser users_page = new PageUser(users,total);
            return Result.success(users_page);
        }else{
            return Result.error("权限不足");
        }

    }

    public Result checklogin(String username, String password) {
        //直接查表
        List<User> userList = userMapper.checklogin(username,password);
        if (userList.size()>0){
            //成功查询到用户,可以进行登录
            int identity = userList.get(0).getRoleId();
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
                    System.out.println(userList);
                    break;
            }
            User user = userList.get(0);
            Map<String, String> claims = getStringStringMap(user);
            String token = JWTUtils.getToken(claims);
            Result result = Result.success();
            result.setData(token);
            result.setMsg(identity_str);
            return result;
        }else {
            Result result = Result.error("登录失败,用户名或密码错误");
            return result;
        }

    }

    /**
     * 一个方法，将user中的内容转换入Map<String,String> claims 中 ，便于token生成
     * @param user
     * @return
     */
    private Map<String, String> getStringStringMap(User user) {
        Map<String, String> claims = new HashMap<>();
        claims.put("username", user.getUsername());
        claims.put("password", user.getPassword());
        claims.put("userId", String.valueOf(user.getId()));
        claims.put("Email", user.getEmail());
        claims.put("phone", user.getPhone());
        claims.put("roleId", String.valueOf(user.getRoleId()));
        return claims;
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

    public Result deleteUser(String id) {
        userMapper.deleteUser(id);
        return Result.success("删除成功");
    }

    public Result deleteUserList(List<String> idList) {
        for (String id:idList){
            userMapper.deleteUser(id);
        }
        return Result.success("删除成功");
    }
}
