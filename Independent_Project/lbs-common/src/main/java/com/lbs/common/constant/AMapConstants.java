package com.lbs.common.constant;

/**
 * @author Eric Xue
 * @since 2023/10/06
 * @describe
 * 此类定义了与高德地图（AMap）相关的常量
 */

public class AMapConstants {
    // 定义一个静态常量，表示高德地图的静态地图API模板
    // 使用这个模板可以通过替换相应的参数来生成地图的URL
    public static final String MAP_TEMPLATE =
            "https://restapi.amap.com/v3/staticmap?markers=mid,0xFF0000,A:{longitude},{latitude}&key={key}";
}
