package com.example.animal_shelet.service;

import com.example.animal_shelet.mapper.AnimalMapper;
import com.example.animal_shelet.pojo.Animal.And.AuditRecords_and_AnimalProfile;
import com.example.animal_shelet.pojo.Animal.And.Shelters_and_AnimalProfiles;
import com.example.animal_shelet.pojo.Animal.AnimalProfile;
import com.example.animal_shelet.pojo.Animal.AuditRecords;
import com.example.animal_shelet.pojo.result.Result;
import com.example.animal_shelet.pojo.Animal.Shelter;
import com.example.animal_shelet.pojo.Animal.PageAnimal;
import com.example.animal_shelet.utils.jwt.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class AnimalService {
    @Autowired
    private AnimalMapper animalMapper;

    public Result getAnimal_record(){
        return Result.success(animalMapper.getAllAnimalList_record());
    }

    public Result getAnimal_bypage(int page, int limit, Shelters_and_AnimalProfiles shelters_and_animalProfiles) {
        int offset = (page - 1) * limit;
        int total = animalMapper.getAllAnimalList_record().size();
        Shelter shelter = shelters_and_animalProfiles.getShelter();
        AnimalProfile animalProfile = shelters_and_animalProfiles.getAnimalProfile();
        List<Shelters_and_AnimalProfiles> animals = animalMapper.getPageAnimals(offset,limit,shelter,animalProfile);
        PageAnimal animals_page = new PageAnimal(animals,total);
        return Result.success(animals_page);
    }

    public int check(int userId) {
        List<Shelter> shelters = animalMapper.selectShelter(userId);
        System.out.println("查询到:"+shelters);
        if (shelters.size()>0){
            return 1;
        }else {
            return 0;
        }
    }

    public int insertAnimalProfile(int shelterId, String animalName, String species, String breed, int gender, int age, String healthStatus, String description, String imgUrl) {
        int result = animalMapper.insertAnimalProfile(shelterId,animalName,species,breed,gender,age,healthStatus,description,imgUrl);
        System.out.println("发布申请插入结果"+result);
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    public Result approvalReleaseAnimalStatus(AuditRecords_and_AnimalProfile auditRecords_and_animalProfile, String token) {
        String userId = JWTUtils.getTokenInfo(token).get("userId");
        String roleId = JWTUtils.getTokenInfo(token).get("roleId");
        if ("1".equals(roleId)){
            AnimalProfile animalProfile = auditRecords_and_animalProfile.getAnimalProfile();
            AuditRecords auditRecords = auditRecords_and_animalProfile.getAuditRecords();
            //更新动物状态
<<<<<<< HEAD
            animalMapper.updateAnimalProfiles(animalProfile);
            log.info("调试参数{}",animalProfile);
=======
            animalMapper.updateAnimalStatus(animalProfile);
>>>>>>> parent of 960ec79 (领养申请记录的创建的接口完成，并且修改部分项目结构)
            //插入审核记录
            extracted(userId, animalProfile, auditRecords);
            animalMapper.interAuditRecords(auditRecords);
            log.info("调试参数{}",auditRecords);
            return Result.success();
        }else {
            return Result.error("权限不足");
         }

    }

    /**
     * 插入审核记录前的参数准备
     * @param userId
     * @param animalProfile
     * @param auditRecords
     */
    private void extracted(String userId, AnimalProfile animalProfile, AuditRecords auditRecords) {
        auditRecords.setAdminId(Integer.valueOf(userId));

        AnimalProfile.AnimalStatus status = animalProfile.getStatus();
        if (status == AnimalProfile.AnimalStatus.AVAILABLE) {
            auditRecords.setAction(AuditRecords.Action.APPROVE);
        } else if (status == AnimalProfile.AnimalStatus.REJECTED) {
            auditRecords.setAction(AuditRecords.Action.REJECT);
        } else {
            // 添加默认处理，避免action为null
            auditRecords.setAction(AuditRecords.Action.APPROVE);
        }

        auditRecords.setTargetType(AuditRecords.Target.PET_PROFILE);

        // 设置描述字段（用于JSON序列化）
        auditRecords.setActionStr(auditRecords.getAction().getDescription());
        auditRecords.setTargetStr(auditRecords.getTargetType().getDescription());
    }


}
