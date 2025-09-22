package com.example.animal_shelet.contoller.pc.Animal;

import com.example.animal_shelet.pojo.Animal.And.AuditRecords_and_AnimalProfile;
import com.example.animal_shelet.pojo.Animal.AnimalProfile;
import com.example.animal_shelet.pojo.Animal.AuditRecords;
import com.example.animal_shelet.pojo.result.Result;
import com.example.animal_shelet.service.AnimalService;
import com.example.animal_shelet.utils.jwt.JWTUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 动物记录控制/pc
 */
@RestController("pcAnimalController")
public class AnimalController {
    @Autowired
    private AnimalService animalService;

    /**
     * 获取动物记录
     * @return
     */
    @RequestMapping("/animalRecord")
    public Result getAnimal_record(){
        return animalService.getAnimal_record();
    }

    /**
     * 分页获取动物记录
     * @param Data
     * @return
     */
    @PostMapping ("/animalRecord_bypage")
    public Result getAnimalsPage(@RequestBody Map<String, Object> Data){
        int page = (int) Data.get("page");
        int limit= (int) Data.get("limit");
        return animalService.getAnimal_bypage(page,limit);
    }

    /**
     * 宠物发布状态的审批
     * @param auditRecords_and_animalProfile
     * @param httpServletRequest
     * @return
     */
    @PostMapping ("/approvalReleaseAnimalStatus")
    public Result approvalReleaseAnimalStatus(@RequestBody AuditRecords_and_AnimalProfile auditRecords_and_animalProfile, HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");
        return animalService.approvalReleaseAnimalStatus(auditRecords_and_animalProfile,token);
    }



}
