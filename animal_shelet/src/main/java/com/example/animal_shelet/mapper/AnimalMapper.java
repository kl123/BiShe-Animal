package com.example.animal_shelet.mapper;

import com.example.animal_shelet.pojo.Animal.AnimalProfile;
import com.example.animal_shelet.pojo.Animal.shelter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AnimalMapper {
    List<AnimalProfile> getAllAnimalList_record();

    List<AnimalProfile> getPageAnimals(@Param("offset") int offset, @Param("limit") int limit);

    List<shelter> selectShelter(@Param("userId")int userId);

    int insertAnimalProfile(int shelterId, String animalName, String species, String breed, int gender, int age, String healthStatus, String description, String imgUrl);
}
