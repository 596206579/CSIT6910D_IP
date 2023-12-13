package com.lbs.service;

import com.lbs.shared.pojo.vo.resp.RespLocationVo;
import com.lbs.shared.pojo.vo.resp.RespMapVo;

import java.util.List;

/**
 * @author Eric Xue
 * @since 2023/10/02
 */
public interface LocationService {
    RespLocationVo getLatestLocation(String userId);

    RespMapVo getLatestMapByLocation(String userId, String device);

    List<RespLocationVo> listLocationsByUserId(String userId);
}
