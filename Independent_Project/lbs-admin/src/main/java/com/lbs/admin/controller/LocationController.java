package com.lbs.admin.controller;

import com.lbs.common.annotation.EnableApiResponse;
import com.lbs.service.LocationService;
import com.lbs.shared.pojo.vo.resp.RespLocationVo;
import com.lbs.shared.pojo.vo.resp.RespMapVo;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * @author Eric Xue
 * @since 2023/10/02
 * @describe:
 * 此类的主要作用是作为位置服务的HTTP接口，处理客户端发来的关于位置信息的请求
 */

@Api(tags = "位置服务")
@EnableApiResponse
@RequestMapping("/v1/locations")
@RequiredArgsConstructor // Lombok的注解，为final字段生成构造方法，Spring将自动注入这些字段
public class LocationController {
    private final LocationService locationService;

    // 处理获取最新位置信息的GET请求
    @GetMapping("/latest")
    public RespLocationVo getLatestLocation(@RequestParam String userId) {
        return locationService.getLatestLocation(userId);
    }

    // 处理获取用户位置列表的GET请求
    @GetMapping("")
    public List<RespLocationVo> listLocations(@RequestParam String userId) {
        return locationService.listLocationsByUserId(userId);
    }

    // 处理根据位置获取最新地图信息的GET请求
    @GetMapping("/map")
    public RespMapVo getLatestMapByLocation(@RequestParam String userId,
                                            @RequestParam String device) {
        return locationService.getLatestMapByLocation(userId, device);
    }
}
