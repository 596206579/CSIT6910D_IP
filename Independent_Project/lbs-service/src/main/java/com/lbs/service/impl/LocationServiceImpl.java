package com.lbs.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lbs.common.constant.AMapConstants;
import com.lbs.common.exception.ApiException;
import com.lbs.common.properties.AMapProperties;
import com.lbs.common.util.BeanUtils;
import com.lbs.common.util.JsonUtils;
import com.lbs.service.LocationService;
import com.lbs.shared.mapper.LocationMapper;
import com.lbs.shared.pojo.entity.Location;
import com.lbs.shared.pojo.vo.resp.RespLocationVo;
import com.lbs.shared.pojo.vo.resp.RespMapVo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author Eric Xue
 * @since 2023/10/02
 * @describe
 * 此类实现了LocationService接口，提供了与位置数据相关的业务逻辑
 */
@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    // 注入LocationMapper，用于与数据库中的位置表进行交互
    private final LocationMapper locationMapper;
    // 注入高德地图的配置属性
    private final AMapProperties aMapProperties;
    // 注入StringRedisTemplate，用于操作Redis中的字符串数据
    private final StringRedisTemplate redisTemplate;

    // 实现LocationService接口的getLatestLocation方法
    @Override
    public RespLocationVo getLatestLocation(String userId) {
        // 从数据库查询用户的最新位置信息
        Location location =
                locationMapper.selectOne(
                        Wrappers.<Location>lambdaQuery()
                                .eq(Location::getUserId, userId)
                                .orderByDesc(Location::getTimestamp)
                                .last("limit 1"));
        // 将Location对象转换为RespLocationVo对象
        return BeanUtils.copy(location, RespLocationVo::new);
    }


    @Override
    // 实现LocationService接口的getLatestMapByLocation方法
    // 先从Redis中进行数据获取，如果没有数据的话，则从数据库中获取，以提高系统的QPS
    public RespMapVo getLatestMapByLocation(String userId, String device) {
        // 构造Redis缓存的key
        String cacheKey;
        if (device.equalsIgnoreCase("ios")) {
            cacheKey = StrUtil.format("location:ios:{}", userId);
        } else if (device.equalsIgnoreCase("android")) {
            cacheKey = StrUtil.format("location:android:{}", userId);
        } else {
            // 如果设备不是iOS或Android，则抛出异常
            throw new ApiException("Not supported device.");
        }

        // 尝试从Redis中获取位置信息
        String cachedLocation = redisTemplate.opsForValue().get(cacheKey);

        Location location;
        if (StrUtil.isBlank(cachedLocation)) {
            // 如果Redis中没有缓存，则从数据库查询
            location =
                    locationMapper.selectOne(
                            Wrappers.<Location>lambdaQuery()
                                    .eq(Location::getUserId, userId)
                                    .eq(Location::getDevice, device.toLowerCase())
                                    .isNotNull(Location::getLongitude)
                                    .isNotNull(Location::getLatitude)
                                    .orderByDesc(Location::getTimestamp)
                                    .last("limit 1"));
        } else {
            // 如果Redis中有缓存，则使用缓存的数据
            location = JsonUtils.fromJson(cachedLocation, Location.class);
        }

        // 检查位置信息是否有效
        if (location == null || location.getLongitude() == null || location.getLatitude() == null) {
            throw new ApiException("Get user latest map error!");
        }

        // 构造高德地图的URL
        String mapUrl =
                StrUtil.format(
                        AMapConstants.MAP_TEMPLATE,
                        new HashMap<String, String>() {
                            {
                                put("longitude", location.getLongitude().toString());
                                put("latitude", location.getLatitude().toString());
                                put("key", aMapProperties.getKey());
                            }
                        });

        // 创建响应对象并返回
        RespMapVo resp = new RespMapVo();
        resp.setMapUrl(mapUrl);
        return resp;
    }

    // 实现LocationService接口的listLocationsByUserId方法
    @Override
    public List<RespLocationVo> listLocationsByUserId(String userId) {
        // 从数据库查询用户的所有位置信息
        List<Location> locations =
                locationMapper.selectList(
                        Wrappers.<Location>lambdaQuery()
                                .eq(Location::getUserId, userId)
                                .orderByDesc(Location::getTimestamp));
        // 将Location对象列表转换为RespLocationVo对象列表
        return BeanUtils.copies(locations, RespLocationVo::new);
    }
}
