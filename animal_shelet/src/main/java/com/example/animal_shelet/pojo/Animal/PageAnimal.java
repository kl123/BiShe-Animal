package com.example.animal_shelet.pojo.Animal;

import com.example.animal_shelet.pojo.Animal.And.Shelters_and_AnimalProfiles;
import com.example.animal_shelet.pojo.Animal.AnimalProfile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageAnimal {
    private List<Shelters_and_AnimalProfiles> animal;
    private int total;
}
