package com.example.animal_shelet.contoller.wechat;

import com.example.animal_shelet.pojo.Animal.AnimalInsertDTO;
import com.example.animal_shelet.pojo.result.Result;
import com.example.animal_shelet.service.AnimalService;
import jakarta.servlet.http.HttpServletRequest;
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
     * @return
     */
    @GetMapping ("/check")
    public Result check(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");
        Map<String,String> map = com.example.animal_shelet.utils.jwt.JWTUtils.getTokenInfo(token);
        int result = animalService.check(Integer.parseInt(map.get("userId")));
        System.out.println("核验资格:"+result);
        if (result==1){
            return Result.success("拥有发布资格");
        }else {
            return Result.error("未开通资格,请前往认证~");
        }
    }

    /**
     * 插入动物信息
     * @param animalInsertDTO
     * @return
     */
    @RequestMapping("/insert_infor")
    public Result insertAnimal_record(@RequestBody AnimalInsertDTO animalInsertDTO, HttpServletRequest request){
        // 强制从token获取shelterId，忽略前端传入的shelterId
        String token = request.getHeader("token");
        if (token == null || token.isEmpty()) {
            return Result.error("发布失败: 未登录或Token无效");
        }

        try {
            Map<String, String> tokenInfo = com.example.animal_shelet.utils.jwt.JWTUtils.getTokenInfo(token);
            if (tokenInfo != null && tokenInfo.containsKey("userId")) {
                int userId = Integer.parseInt(tokenInfo.get("userId"));
                
                // 查询用户关联的流浪所ID
                Integer shelterId = animalService.getShelterIdByUserId(userId);
                
                if (shelterId == null) {
                    return Result.error("发布失败: 当前用户未绑定任何流浪所，无权发布");
                }
                
                // 强制设置 shelterId
                animalInsertDTO.setShelterId(shelterId);
            } else {
                return Result.error("发布失败: Token信息无法解析");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("发布失败: 系统异常 - " + e.getMessage());
        }

        //进行插入
        int result = animalService.insertAnimalProfile(animalInsertDTO);

        //判断
        if (result == 1){
            return Result.success("插入成功");
        }else {
            return Result.error("插入失败");
        }
    }

    /**
     * 获取所有可领养的动物信息
     * @return
     */
    @GetMapping("/getAvailableAnimals")
    public Result getAvailableAnimals(){
        return animalService.getAvailableAnimals();
    }



}
