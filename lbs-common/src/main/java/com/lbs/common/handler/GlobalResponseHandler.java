package com.lbs.common.handler;

import cn.hutool.core.util.ObjectUtil;
import com.lbs.common.ApiResponse;
import com.lbs.common.annotation.EnableApiResponse;
import com.lbs.common.util.JsonUtils;
import com.lbs.common.annotation.DisableApiResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Method;

/**
 * @author Eric Xue
 * @date 2021/06/08
 * @describe
 * 此类的作用是提供一个统一的方式来处理和修改所有标记了@EnableApiResponse的控制器方法的响应
 */
@RestControllerAdvice(annotations = EnableApiResponse.class)
public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {

    // 检查这个处理器是否应该应用于当前的响应
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 如果返回类型已经是ApiResponse，就不需要再次包装
        if (ObjectUtil.isNotNull(returnType.getMethod())
                && ApiResponse.class.equals(returnType.getMethod().getReturnType())) {
            return false;
        }
        // 如果方法上有@DisableApiResponse注解，也不进行处理
        return ObjectUtil.isNull(returnType.getMethodAnnotation(DisableApiResponse.class));
    }

    // 在响应体写入之前调用这个方法来修改响应体
    @Override
    public Object beforeBodyWrite(
            Object body,
            MethodParameter returnType,
            MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType,
            ServerHttpRequest request,
            ServerHttpResponse response) {

        // 包装原始响应体到ApiResponse中
        ApiResponse<Object> apiResponse = ApiResponse.success(body);
        Method method = returnType.getMethod();
        // 如果方法的返回类型是String，特殊处理以避免类型转换错误
        if (ObjectUtil.isNotNull(method)
                && String.class.equals(method.getReturnType())) {
            // 设置响应类型为JSON，并将ApiResponse对象转换为JSON字符串
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return JsonUtils.toJson(apiResponse);
        }
        // 对于非String类型的返回值，直接返回包装后的ApiResponse
        return apiResponse;
    }
}
