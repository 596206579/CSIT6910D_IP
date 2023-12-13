package com.lbs.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Eric Xue
 * @since 2023/10/06
 */
@ConfigurationProperties(prefix = "amap") // 标记这个类为配置属性类，并指定配置文件中的前缀为"amap"
@Component
@Data
public class AMapProperties {
    private String key;
}
