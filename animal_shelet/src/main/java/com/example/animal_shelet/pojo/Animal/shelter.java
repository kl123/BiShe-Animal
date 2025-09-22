package com.example.animal_shelet.pojo.Animal;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class shelter {
    private int id;
    private String shelter_name;
    private String contact_person;
    private String business_license;
    private String qualification_file;
    private Integer userId;
}
