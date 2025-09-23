package com.example.animal_shelet.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AdoptersMapper {

    void insertAdoptionApplications(@Param("userId") String userId, @Param("animalId") Integer animalId);
}
