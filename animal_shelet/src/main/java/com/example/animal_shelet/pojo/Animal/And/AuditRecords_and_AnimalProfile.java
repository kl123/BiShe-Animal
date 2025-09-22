package com.example.animal_shelet.pojo.Animal.And;

import com.example.animal_shelet.pojo.Animal.AnimalProfile;
import com.example.animal_shelet.pojo.Animal.AuditRecords;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditRecords_and_AnimalProfile {
    private AuditRecords auditRecords;
    private AnimalProfile animalProfile;
}
