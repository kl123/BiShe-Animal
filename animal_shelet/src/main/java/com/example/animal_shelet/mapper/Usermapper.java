package com.example.animal_shelet.mapper;

import com.example.animal_shelet.pojo.User.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface Usermapper {

    List<User> getPageuser(@Param("offset") int offset, @Param("limit") int limit);

    List<User> checklogin(@Param("username") String username, @Param("password") String password);

    List<User> selectAll();

    int countByUsername(@Param("username")String username);

    int countByPhone(String phone);

    void register(String username, String phone, String password, String email);
}
