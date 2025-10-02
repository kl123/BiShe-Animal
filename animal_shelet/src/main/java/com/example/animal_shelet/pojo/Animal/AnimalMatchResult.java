package com.example.animal_shelet.pojo.Animal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 动物匹配结果实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalMatchResult {
    private AnimalProfile animal; // 动物信息
    private Double matchPercentage; // 适配百分比 (0-100)
    private String matchReason; // 匹配原因说明
}