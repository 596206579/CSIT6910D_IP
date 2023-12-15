package com.lbs.shared.pojo.dto;

import lombok.Data;

/**
 * @author Eric Xue
 * @since 2023/09/29
 * @describe
 * 惯性测量单元（Inertial Measurement Unit）
 * 这些数据通常用于实现位置跟踪、运动检测、方向控制等功能。
 */
@Data
public class ImuDto {
    private Double x;
    private Double y;
    private Double z;
}
