package com.example.animal_shelet.pojo.Animal;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
//  @JsonIgnore
  private Target targetType;
  private String targetStr;
  private Integer targetId;
//  @JsonIgnore
  private Action action;
  private String actionStr;
  private String reason;
  private LocalDateTime auditTime;

  public enum Target {
      USER(1, "用户"),
      POST(2, "帖子"),
      COMMENT(3, "评论"),
      PET_PROFILE(4, "宠物档案");

      private final int value;
      private final String description;

      Target(int value, String description) {
          this.value = value;
          this.description = description;
      }

      public int getValue() {
          return value;
      }

      public String getDescription() {
          return description;
      }

      public static Target fromValue(int value) {
          for (Target type : Target.values()) {
              if (type.value == value) {
                  return type;
              }
          }
          throw new IllegalArgumentException("未知的目标类型: " + value);
      }
  }

  public enum Action {
      REJECT(0, "拒绝"),
      APPROVE(1, "通过");

      private final int value;
      private final String description;

      Action(int value, String description) {
          this.value = value;
          this.description = description;
      }

      public int getValue() {
          return value;
      }

      public String getDescription() {
          return description;
      }

      public static Action fromValue(int value) {
          for (Action type : Action.values()) {
              if (type.value == value) {
                  return type;
              }
          }
          throw new IllegalArgumentException("未知的目标类型: " + value);
      }
  }


}
