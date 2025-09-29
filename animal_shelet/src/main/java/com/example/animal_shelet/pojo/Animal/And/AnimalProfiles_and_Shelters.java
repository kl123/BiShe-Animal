package com.example.animal_shelet.pojo.Animal.And;

import com.example.animal_shelet.pojo.Animal.AnimalProfile;
import com.example.animal_shelet.pojo.Animal.Shelter;
import com.example.animal_shelet.pojo.User.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalProfiles_and_Shelters {
    private AnimalProfile animalProfile;
    private Shelter shelter;
    private User user;
}
