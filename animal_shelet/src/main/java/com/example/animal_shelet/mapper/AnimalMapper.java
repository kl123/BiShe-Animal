package com.example.animal_shelet.mapper;

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

    List<AnimalProfile> getPageAnimals_IsNull(@Param("offset") int offset, @Param("limit") int limit);

    List<Shelter> selectShelter(@Param("userId")int userId);

    int insertAnimalProfile(int shelterId, String animalName, String species, String breed, int gender, int age, String healthStatus, String description, String imgUrl);

    void updateAnimalProfiles(AnimalProfile animalProfile);

   void updateAnimalProfilesStatus(
       @Param("status") Integer status,
       @Param("animalId") Integer animalId
   );

    void interAuditRecords(AuditRecords auditRecords);

    List<AnimalProfile> getPageAnimals_NotNull(@Param("offset") int offset, @Param("limit") int limit, @Param("animalProfile") AnimalProfile animalProfile);
}
