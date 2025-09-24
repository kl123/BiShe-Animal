package com.example.animal_shelet.pojo.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
//用户表
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Integer id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private Integer roleId;
    private LocalDateTime created_at;
    private LocalDateTime update_at;


}
