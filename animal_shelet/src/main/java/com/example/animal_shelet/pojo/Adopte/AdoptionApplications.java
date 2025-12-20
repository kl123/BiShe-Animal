package com.example.animal_shelet.pojo.Adopte;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdoptionApplications {

  private Integer id;
  private Integer adopterId;
  private Integer animalId;
  private LocalDateTime applicationDate;
  private Integer status;
  private String feedback;
  private LocalDateTime completionDate;

  // 扩展字段，用于展示
  private String animalName;
  private String animalImageUrl;
  private String shelterName;

}
