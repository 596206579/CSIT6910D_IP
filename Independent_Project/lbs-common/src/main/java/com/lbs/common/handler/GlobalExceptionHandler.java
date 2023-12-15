package com.lbs.common.handler;

import cn.hutool.core.util.StrUtil;
import com.lbs.common.ApiResponse;
import com.lbs.common.ResponseCodeEnum;
import com.lbs.common.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author Eric Xue
 * @date 2021/06/07
 * @describe
 * 此类是一个全局异常处理器，用于拦截和处理Spring MVC应用中的各种异常。
 */
@Slf4j
@RestControllerAdvice // 标记为Spring MVC的控制器建议类，用于全局异常处理
public class GlobalExceptionHandler {
    // 处理ApiException异常的方法
    @ExceptionHandler(value = ApiException.class)
    public ApiResponse<Object> apiExceptionHandler(ApiException apiException) {
        // 创建响应对象
        ApiResponse<Object> apiResponse = new ApiResponse<>();
        // 设置响应失败标记
        apiResponse.setSuccess(false);
        // 设置异常中的错误码
        apiResponse.setCode(apiException.getCode());
        // 设置异常信息
        apiResponse.setMsg(apiException.getMessage());

        return apiResponse;
    }

    // 处理BindException异常的方法
    @ExceptionHandler(value = BindException.class)
    public ApiResponse<Object> bindExceptionHandler(BindException bindException) {
        ApiResponse<Object> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(false);
        // 设置HTTP请求错误的错误码
        apiResponse.setCode(ResponseCodeEnum.BAD_REQUEST.getCode());
        // 获取并设置字段绑定错误信息
        apiResponse.setMsg(
                bindException.getFieldErrors().stream()
                        .map(fieldError -> fieldError.getField() + fieldError.getDefaultMessage())
                        .collect(Collectors.joining(",")));

        return apiResponse;
    }

    // 处理ConstraintViolationException异常的方法
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ApiResponse<Object> constraintViolationException(ConstraintViolationException exception) {
        ApiResponse<Object> apiResponse = new ApiResponse<>();
        // 提取和格式化验证违反的错误信息
        String errorMessages =
                exception.getConstraintViolations().stream()
                        .map(violation ->
                                StrUtil.format("{} {}",
                                        StreamSupport.stream(violation.getPropertyPath().spliterator(), false)
                                                .reduce((first, second) -> second)
                                                .orElse(null),
                                        violation.getMessage()))
                        .collect(Collectors.joining(";"));

        apiResponse.setSuccess(false);
        apiResponse.setCode(ResponseCodeEnum.BAD_REQUEST.getCode());
        apiResponse.setMsg(errorMessages);

        return apiResponse;
    }

    // 处理MethodArgumentNotValidException异常的方法
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ApiResponse<Object> methodArgumentNotValidExceptionHandler(
            MethodArgumentNotValidException exception) {
        // 获取绑定结果和字段错误
        BindingResult bindingResult = exception.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        // 提取和格式化字段错误信息
        String errorMessages =
                fieldErrors.stream()
                        .map(error -> error.getField() + " " + error.getDefaultMessage())
                        .sorted()
                        .collect(Collectors.joining("; "));

        ApiResponse<Object> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(false);
        apiResponse.setMsg(errorMessages);
        apiResponse.setCode(ResponseCodeEnum.BAD_REQUEST.getCode());
        return apiResponse;
    }

    // 处理通用Exception的方法
    @ExceptionHandler(value = Exception.class)
    public Object exceptionHandler(Exception exception) {
        // 打印异常栈信息（通常在生产环境中应避免这样做）
        exception.printStackTrace();

        // 创建通用响应对象
        ApiResponse<Object> result = new ApiResponse<>();
        result.setSuccess(false);
        result.setCode(ResponseCodeEnum.INTERNAL_SERVER_ERROR.getCode());
        result.setMsg(ResponseCodeEnum.INTERNAL_SERVER_ERROR.getMessage());
        return result;
    }
}
