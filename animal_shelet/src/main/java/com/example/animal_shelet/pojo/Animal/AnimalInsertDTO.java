package com.example.animal_shelet.pojo.Animal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AnimalInsertDTO {
    private Integer shelterId;

    @JsonProperty("Animalname")
    private String animalName;

    private String species;
    private String breed;
    private Integer gender;
    private Integer age;
    private String healthStatus;
    private String description;
    private String imgUrl;
}
