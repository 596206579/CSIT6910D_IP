package com.lbs.common.annotation;

import java.lang.annotation.*;

/**
 * 禁用自定义的统一API响应
 *
 * @author Eric Xue
 * @date 2023/09/07
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)  // 指定这个注解在运行时仍然可见
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface DisableApiResponse {
}
