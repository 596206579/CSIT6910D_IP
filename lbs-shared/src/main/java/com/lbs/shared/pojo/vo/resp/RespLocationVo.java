package com.lbs.shared.pojo.vo.resp;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.lbs.shared.enums.DeviceEnum;
import lombok.Data;

import java.util.Date;

/**
 * @author Eric Xue
 * @since 2023/10/02
 */
@Data
public class RespLocationVo {
    private Long id;
    private String userId;
    private DeviceEnum device;
    private Double longitude;
    private Double latitude;
    private String ssid;
    private Double rssi;
    private String imu;
    private Date timestamp;
}
