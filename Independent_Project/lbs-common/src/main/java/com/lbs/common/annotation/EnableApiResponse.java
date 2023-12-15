package com.lbs.common.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

/**
 * 开启自定义的统一API响应
 *
 * @author Eric Xue
 * @date 2021/02/07
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)  // 指定这个注解在运行时仍然可见
@Documented
@RestController
public @interface EnableApiResponse {
    /**
     * The value may indicate a suggestion for a logical component name,
     * to be turned into a Spring bean in case of an autodetected component.
     * @return the suggested component name, if any (or empty String otherwise)
     * @since 4.0.1
     */
    @AliasFor(annotation = RestController.class)
    String value() default "";
}
