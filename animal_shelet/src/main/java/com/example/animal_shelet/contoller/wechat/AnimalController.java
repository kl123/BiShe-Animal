package com.example.animal_shelet.contoller.wechat;

import com.example.animal_shelet.pojo.result.Result;
import com.example.animal_shelet.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 动物记录控制/wechat
 */
@RestController("wechatAnimalController")
public class AnimalController {
    @Autowired
    private AnimalService animalService;


    /**
     * 核验资格
     * @param Data
     * @return
     */
    @PostMapping ("/check")
    public Result check(@RequestBody Map<String, Object> Data){
        //获取
        int userId = (int)Data.get("userid");
        int result = animalService.check(userId);
        System.out.println("核验资格:"+result);
        if (result==1){
            return Result.success("拥有发布资格");
        }else {
            return Result.error("未开通资格,请前往认证~");
        }
    }

    /**
     * 插入动物信息
     * @param Data
     * @return
     */
    @RequestMapping("/insert_infor")
    public Result insertAnimal_record(@RequestBody Map<String, Object> Data){
        int shelterId = (int)Data.get("shelterId");
        String Animalname = (String) Data.get("Animalname");
        String species = (String) Data.get("species");
        String breed = (String)Data.get("breed");
        int gender = (int)Data.get("gender");
        int age = (int)Data.get("age");
        String healthStatus = (String)Data.get("healthStatus");
        String description = (String) Data.get("description");
        String imgUrl = (String) Data.get("imgUrl");

        //进行插入
        int result = animalService.insertAnimalProfile(shelterId,Animalname,species,breed,gender,age,healthStatus,description,imgUrl);

        //判断
        if (result == 1){
            return Result.success("插入成功");
        }else {
            return Result.error("插入失败");
        }
    }



}
