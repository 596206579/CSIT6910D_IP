package com.lbs.shared.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.lbs.shared.enums.DeviceEnum;
import lombok.Data;

import java.util.Date;

/**
 * @author Eric Xue
 * @since 2023/10/02
 */
@Data
@TableName("location")
public class Location {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String userId;
    private DeviceEnum device;
    private Double longitude;
    private Double latitude;
    private String ssid;
    private Double rssi;
    private String imu;

    @TableField(fill = FieldFill.INSERT)
    private Date timestamp;
}
