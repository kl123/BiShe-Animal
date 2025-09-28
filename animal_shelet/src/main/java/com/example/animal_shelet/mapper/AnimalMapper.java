package com.example.animal_shelet.mapper;

import com.example.animal_shelet.pojo.Animal.And.Shelters_and_AnimalProfiles;
import com.example.animal_shelet.pojo.Animal.AnimalProfile;
import com.example.animal_shelet.pojo.Animal.AuditRecords;
import com.example.animal_shelet.pojo.Animal.Shelter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AnimalMapper {
    List<AnimalProfile> getAllAnimalList_record();


    List<Shelter> selectShelter(@Param("userId")int userId);

    int insertAnimalProfile(int shelterId, String animalName, String species, String breed, int gender, int age, String healthStatus, String description, String imgUrl);

    void updateAnimalStatus(AnimalProfile animalProfile);

    void interAuditRecords(AuditRecords auditRecords);

    List<Shelters_and_AnimalProfiles> getPageAnimals(int offset, int limit, Shelter shelter, AnimalProfile animalProfile);
}
