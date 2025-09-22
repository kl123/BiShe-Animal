package com.example.animal_shelet.pojo.Animal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalProfile {
    private int id; // 主键
    private int shelterId; // 所属流浪所ID
    private String shelterName;//所属流浪所名字
    private String contactPerson;//流浪所联系人
    private String name; // 宠物名字
    private String species; // 物种
    private String breed; // 品种
    private int gender; // 性别（0:雌, 1:雄）
    private double age; // 年龄（年）
    private String healthStatus; // 健康状况
    private String description; // 描述
    private String imageUrl; // 图片路径
    private int status; // 状态（0:待审核, 1:可领养, 2:已领养, 3:审核不通过）
    private String statusStr;
    private LocalDateTime createdAt; // 创建时间
    private LocalDateTime updatedAt; // 更新时间
}
