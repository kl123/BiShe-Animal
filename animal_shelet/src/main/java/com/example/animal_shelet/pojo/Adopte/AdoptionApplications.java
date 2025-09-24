package com.example.animal_shelet.pojo.Adopte;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
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
  @JsonIgnore
  private AdoptionApplicationsStatus status;
  private String statusStr;
  private String feedback;
  private LocalDateTime completionDate;

  public enum AdoptionApplicationsStatus {
    PENDING(0, "待处理"),
    APPROVED(1, "通过"),
    REJECTED(2, "拒绝"),
    FINISHED(3, "完成"),
    UNKNOWN(-1, "未知状态");

    @Getter
    private int code;
    @Getter
    private String description;

    AdoptionApplicationsStatus(int code, String description) {
      this.code = code;
      this.description = description;
    }
    public static AdoptionApplicationsStatus fromCode(int code) {
      for ( AdoptionApplicationsStatus status : values()) {
        if (status.code == code) {
          return status;
        }
      }
      return UNKNOWN;
    }

    public static AdoptionApplicationsStatus fromDescription(String description) {
      for (AdoptionApplicationsStatus status : values()) {
        if (status.description.equals(description)) {
          return status;
        }
      }
      return UNKNOWN;
    }
  }
}
