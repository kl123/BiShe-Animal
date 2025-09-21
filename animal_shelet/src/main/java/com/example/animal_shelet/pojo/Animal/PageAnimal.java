package com.example.animal_shelet.pojo.Animal;

import com.example.animal_shelet.pojo.Animal.AnimalProfile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageAnimal {
    private List<AnimalProfile> animal;
    private int total;
}
