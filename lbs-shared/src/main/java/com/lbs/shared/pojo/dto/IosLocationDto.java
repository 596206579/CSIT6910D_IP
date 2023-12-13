package com.lbs.shared.pojo.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author Eric Xue
 * @since 2023/09/29
 */
@Data
public class IosLocationDto {
    private String userId;
    private Double longitude;
    private Double latitude;
    private String ssid;
    private Double rssi;
    private ImuDto imu;
    private Date timestamp;
}
