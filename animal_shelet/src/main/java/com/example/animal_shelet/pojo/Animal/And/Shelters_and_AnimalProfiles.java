package com.example.animal_shelet.pojo.Animal.And;

import com.example.animal_shelet.pojo.Animal.AnimalProfile;
import com.example.animal_shelet.pojo.Animal.Shelter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shelters_and_AnimalProfiles {
    private Shelter shelter;
    private AnimalProfile animalProfile;
}
