package com.lbs.admin.listener;

import cn.hutool.core.util.StrUtil;
import com.lbs.common.util.JsonUtils;
import com.lbs.shared.enums.DeviceEnum;
import com.lbs.shared.mapper.LocationMapper;
import com.lbs.shared.pojo.dto.IosLocationDto;
import com.lbs.shared.pojo.entity.Location;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author Eric Xue
 * @since 2023/09/27
 * @describe:
 * 此类的作用是作为一个Kafka消息消费者，专门处理来自某个主题的位置信息。
 * 通过@KafkaListener注解，它监听“ios-location”主题，自动接收该主题的消息。
 */
@Component
@RequiredArgsConstructor
public class LocationConsumer {
    // 注入LocationMapper，用于将位置数据插入数据库
    private final LocationMapper locationMapper;
    // 注入StringRedisTemplate，用于操作Redis中的字符串数据
    private final StringRedisTemplate stringRedisTemplate;

    @KafkaListener(
            topics = "ios-location",
            groupId = "ios-location",
            containerFactory = "iosLocationContainerFactory")
    public void ios(IosLocationDto iosLocationDto) {
        // 创建Location实体，用于保存位置信息
        Location location = new Location();
        // 设置Location实体的属性
        location.setUserId(iosLocationDto.getUserId());
        location.setDevice(DeviceEnum.IOS);
        location.setLongitude(iosLocationDto.getLongitude());
        location.setLatitude(iosLocationDto.getLatitude());
        location.setSsid(iosLocationDto.getSsid());
        location.setRssi(iosLocationDto.getRssi());
        location.setImu(JsonUtils.toJson(iosLocationDto.getImu()));
        location.setTimestamp(iosLocationDto.getTimestamp());
        // 将位置信息插入数据库
        locationMapper.insert(location);

        // 构建Redis中的缓存key
        String cacheKey = StrUtil.format("location:ios:{}", iosLocationDto.getUserId());
        // 将位置信息缓存到Redis
        stringRedisTemplate.opsForValue().set(cacheKey, JsonUtils.toJson(location));
    }
}
