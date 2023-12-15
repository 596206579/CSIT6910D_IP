package com.lbs.admin.config;

import com.lbs.common.config.BaseSwaggerConfiguration;
import com.lbs.common.constant.SpringConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;

/**
 * @author Eric Xue
 * @date 2021/05/14
 * @describe:
 * 此类是项目中Swagger API文档配置的关键部分，它确保了在开发和测试阶段，
 * API的使用者都能够访问完整且格式化的API文档，从而更好地了解和使用系统的后端接口。
 */

@Configuration
// 指定这个配置仅在开发（dev）和测试（test）环境中生效
@Profile({SpringConstants.DEV_PROFILE, SpringConstants.TEST_PROFILE})
public class SwaggerConfiguration extends BaseSwaggerConfiguration {
    // 实现基类中的apiInfo方法，用于提供API的基本信息
    @Override
    protected ApiInfo apiInfo() {
        // 构建并返回API信息，包括API的标题、描述和版本
        return new ApiInfoBuilder().title("后台接口").description("模板后台接口").version("1.0.0").build();
    }
}
