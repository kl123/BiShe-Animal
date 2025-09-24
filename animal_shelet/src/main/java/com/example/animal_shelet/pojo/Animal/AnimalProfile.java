package com.example.animal_shelet.pojo.Animal;

import com.fasterxml.jackson.annotation.JsonCreator;
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
public class AnimalProfile {
    private Integer id; // 主键
    private Integer shelterId; // 所属流浪所ID
    private String shelterName;//所属流浪所名字
    private String contactPerson;//流浪所联系人
    private String name; // 宠物名字
    private String species; // 物种
    private String breed; // 品种
    private Gender gender; // 性别（0:雌, 1:雄）
    private String genderStr;
    private Double age; // 年龄（年）
    private String healthStatus; // 健康状况
    private String description; // 描述
    private String imageUrl; // 图片路径
    private AnimalStatus status; // 状态（0:待审核, 1:可领养, 2:已领养, 3:审核不通过）
    private String statusStr;
    private LocalDateTime createdAt; // 创建时间
    private LocalDateTime updatedAt; // 更新时间


    public enum AnimalStatus {
        PENDING(0, "待审核"),
        AVAILABLE(1, "可领养"),
        ADOPTED(2, "已领养"),
        REJECTED(3, "审核不通过"),
        UNKNOWN(-1, "未知状态");

        @Getter private final int code;
        @Getter private final String description;

        AnimalStatus(int code, String description) {
            this.code = code;
            this.description = description;
        }

        public static AnimalStatus fromCode(int code) {
            for (AnimalStatus status : values()) {
                if (status.code == code) {
                    return status;
                }
            }
            return UNKNOWN;
        }

        public static AnimalStatus fromDescription(String description) {
            for (AnimalStatus status : values()) {
                if (status.description.equals(description)) {
                    return status;
                }
            }
            return UNKNOWN;
        }


    }

    public enum Gender {
        FEMALE(0, "雌"),
        MALE(1, "雄"),
        UNKNOWN(-1, "未知性别");

        @Getter
        private final int code;
        @Getter
        private final String description;

        Gender(int code, String description) {
            this.code = code;
            this.description = description;
        }

        public static Gender fromCode(int code) {
            for (Gender gender : values()) {
                if (gender.code == code) {
                    return gender;
                }
            }
            return UNKNOWN;
        }

        public static Gender fromDescription(String description) {
            for (Gender gender : values()) {
                if (gender.description.equals(description)) {
                    return gender;
                }
            }
            return UNKNOWN;
        }


    }
}
