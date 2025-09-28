package com.example.animal_shelet.mapper;

import com.example.animal_shelet.pojo.Animal.And.Shelters_and_AnimalProfiles;
import com.example.animal_shelet.pojo.Audit.And.AuditRecords_and_username;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AuditMapper {
    List<Shelters_and_AnimalProfiles> getApplicationInformation(@Param("userId") String userId);

    List<AuditRecords_and_username> getAllAuditInfoList();
}
