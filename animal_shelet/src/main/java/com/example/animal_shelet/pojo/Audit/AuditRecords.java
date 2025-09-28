package com.example.animal_shelet.pojo.Audit;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditRecords {
  private Integer id;
  private Integer adminId;
  private Integer targetType;
  private Integer targetId;
  private Integer action;
  private String reason;
  private LocalDateTime auditTime;
}
