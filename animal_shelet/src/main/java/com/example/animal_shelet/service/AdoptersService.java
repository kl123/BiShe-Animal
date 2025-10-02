package com.example.animal_shelet.service;
import com.example.animal_shelet.mapper.AnimalMapper;
import com.example.animal_shelet.utils.jwt.JWTUtils;
import com.example.animal_shelet.mapper.AdoptersMapper;
import com.example.animal_shelet.pojo.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class AdoptersService {
    @Autowired
    private AdoptersMapper  adoptersMapper;
    @Autowired
    private AnimalMapper animalMapper;

    @Transactional(rollbackFor = Exception.class)
    public Result insertAdoptionApplications(String token, Integer animalId) {
        Map<String, String> tokenInfo = JWTUtils.getTokenInfo(token);
        String userId = tokenInfo.get("userId");
        // 乐观锁：尝试将状态从可领养(1)更新为已领养(2)
        int updated = animalMapper.tryUpdateAnimalProfilesStatus(1, 2, animalId);
        if (updated == 0) {
            // 更新失败，说明被其他用户抢先更新
            return Result.error("该宠物已被其他用户申请，请稍后再试");
        }
        // 状态更新成功，插入申请记录（同一事务内，若插入失败将整体回滚）
        adoptersMapper.insertAdoptionApplications(userId, animalId);
        return Result.success();
    }
}
