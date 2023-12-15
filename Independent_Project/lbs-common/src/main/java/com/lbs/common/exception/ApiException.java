package com.lbs.common.exception;

import com.lbs.common.ResponseCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定义的API异常
 *
 * @author Eric Xue
 * @date 2021/02/08
 * @describe
 * 自定义异常类
 */
@EqualsAndHashCode(callSuper = false) // Lombok注解，生成equals和hashCode方法，但不包括父类属性
@AllArgsConstructor // Lombok注解，生成全参数构造函数
@Data
public class ApiException extends RuntimeException {
    private String message;
    private Integer code;

    // 无参构造函数，提供默认的异常信息
    public ApiException() {
        this("A server error occurred.");
    }

    // 带有消息参数的构造函数
    public ApiException(String message) {
        this(message, ResponseCodeEnum.SUCCESS.getCode());
    }

    // 基于ApiExceptionEnum枚举的构造函数
    public ApiException(ApiExceptionEnum exception) {
        this(exception.getMessage(), ResponseCodeEnum.SUCCESS.getCode());
    }
}
