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
        adoptersMapper.insertAdoptionApplications(userId, animalId);
        animalMapper.updateAnimalProfilesStatus(0, animalId);
        return Result.success();
    }
}
