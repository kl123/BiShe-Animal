package com.example.animal_shelet.pojo.Animal;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditRecords {

  private Integer id;
  private Integer adminId;
  private Integer targetType;
  private Integer targetId;
  private Integer action;
  private String reason;
  private LocalDateTime auditTime;

}
