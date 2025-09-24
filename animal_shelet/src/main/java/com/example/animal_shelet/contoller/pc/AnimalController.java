package com.example.animal_shelet.contoller.pc;

import com.example.animal_shelet.pojo.Animal.And.AuditRecords_and_AnimalProfile;
import com.example.animal_shelet.pojo.Animal.And.Shelters_and_AnimalProfiles;
import com.example.animal_shelet.pojo.Animal.AnimalProfile;
import com.example.animal_shelet.pojo.Animal.AuditRecords;
import com.example.animal_shelet.pojo.result.Result;
import com.example.animal_shelet.service.AnimalService;
import com.example.animal_shelet.utils.jwt.JWTUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 动物记录控制/pc
 */
@Slf4j
@RestController("pcAnimalController")
public class AnimalController {
    @Autowired
    private AnimalService animalService;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getAnimalsPage_AnimalFilesPageLimit{
        private int page;
        private int limit;
        private Shelters_and_AnimalProfiles shelters_and_animalProfiles;
    }

    /**
     * 获取动物记录
     * @return
     */
    @RequestMapping("/animalRecord")
    public Result getAnimal_record(){
        return animalService.getAnimal_record();
    }

    /**
     * 分页可条件查询动物记录
     * @param Data
     * @return
     */
    @PostMapping ("/animalRecord_bypage")
    public Result getAnimalsPage(@RequestBody getAnimalsPage_AnimalFilesPageLimit Data){
        int page = Data.getPage();
        int limit= Data.getLimit();
        Shelters_and_AnimalProfiles shelters_and_animalProfiles = Data.getShelters_and_animalProfiles();
        return animalService.getAnimal_bypage(page,limit,shelters_and_animalProfiles);
    }

    /**
     * 宠物发布状态的审批
     * @param auditRecords_and_animalProfile
     * @param httpServletRequest
     * @return
     */
    @PostMapping ("/approvalReleaseAnimalStatus")
    public Result approvalReleaseAnimalStatus(@RequestBody AuditRecords_and_AnimalProfile auditRecords_and_animalProfile, HttpServletRequest httpServletRequest){
        log.info("auditRecords_and_animalProfile:{}",auditRecords_and_animalProfile);
        String token = httpServletRequest.getHeader("token");
        return animalService.approvalReleaseAnimalStatus(auditRecords_and_animalProfile,token);
    }


}
