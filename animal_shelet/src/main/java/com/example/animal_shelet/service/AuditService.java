package com.example.animal_shelet.service;

import com.example.animal_shelet.mapper.AuditMapper;
import com.example.animal_shelet.pojo.Animal.And.Shelters_and_AnimalProfiles;
import com.example.animal_shelet.pojo.Animal.AuditRecords;
import com.example.animal_shelet.pojo.result.Result;
import com.example.animal_shelet.utils.jwt.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 审核控制
 */
@Service
public class AuditService {
    @Autowired
    private AuditMapper auditMapper;

    public Result getApplicationInformation(String token) {
        Map<String, String> tokenInfo = JWTUtils.getTokenInfo(token);
        String userId = tokenInfo.get("userId");
        List<Shelters_and_AnimalProfiles> shelters_and_animalProfiles_List_List = auditMapper.getApplicationInformation(userId);
        return Result.success(shelters_and_animalProfiles_List_List);
    }

    public Result getAuditRecords(String userId) {
        List<AuditRecords> auditRecords_List = auditMapper.getAuditRecords(userId);
        return Result.success(auditRecords_List);
    }
}
