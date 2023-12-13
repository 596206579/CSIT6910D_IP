package com.lbs.common;

import lombok.Data;

/**
 * 接口通用返回结果
 *
 * @author Eric Xue
 * @date 2021/05/14
 * @describe
 * 此类的作用是为API响应提供一个统一的数据格式
 */
@Data
public class ApiResponse<T> {
    // 响应码
    private Integer code;

    private Boolean success;

    private String msg;

    // 泛型字段，用于存放具体的响应数据
    private T data;

    // 生成一个表示请求成功的ApiResponse对象
    public static ApiResponse<Boolean> success() {
        ApiResponse<Boolean> apiResponse = new ApiResponse<>();
        apiResponse.setCode(ResponseCodeEnum.SUCCESS.getCode());
        apiResponse.setSuccess(true);
        apiResponse.setMsg(ResponseCodeEnum.SUCCESS.getMessage());
        return apiResponse;
    }

    // 生成一个携带具体数据且表示请求成功的ApiResponse对象
    public static <T> ApiResponse<T> success(T data) {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.setCode(ResponseCodeEnum.SUCCESS.getCode());
        apiResponse.setSuccess(true);
        apiResponse.setMsg(ResponseCodeEnum.SUCCESS.getMessage());
        apiResponse.setData(data);
        return apiResponse;
    }

    // 静态方法，用于生成一个表示权限验证失败的ApiResponse对象
    public static ApiResponse<Boolean> unauthorized() {
        ApiResponse<Boolean> apiResponse = new ApiResponse<>();
        apiResponse.setCode(ResponseCodeEnum.UNAUTHORIZED.getCode());
        apiResponse.setMsg(ResponseCodeEnum.UNAUTHORIZED.getMessage());
        apiResponse.setSuccess(false);
        apiResponse.setData(null);
        return apiResponse;
    }
}
