package com.example.animal_shelet.service;

import com.example.animal_shelet.mapper.AuditMapper;
import com.example.animal_shelet.pojo.Animal.And.AuditRecords_and_AnimalProfile;
import com.example.animal_shelet.pojo.Animal.And.Shelters_and_AnimalProfiles;
import com.example.animal_shelet.pojo.Animal.AuditRecords;
import com.example.animal_shelet.pojo.Audit.And.AuditRecords_and_username;
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

    public Result getAllAuditInfoList(Map<String, String> tokenInfo) {
        String RoleId = tokenInfo.get("roleId");
        if ("1".equals(RoleId)) {
            List<AuditRecords_and_username> shelters_and_animalProfiles_List_List = auditMapper.getAllAuditInfoList();
            return Result.success(shelters_and_animalProfiles_List_List);
        } else {
            return Result.error("权限不足");
        }
    }
}
