package com.example.animal_shelet.pojo.Audit.And;

import com.example.animal_shelet.pojo.Audit.AuditRecords;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditRecords_and_username {
    private AuditRecords auditRecords;
    private String username;
    private Integer auditRecordsId;

}
