package com.lbs.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * Json 序列化、反序列化工具
 *
 * @author Eric Xue
 * @version 1.0.0
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JsonUtils {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        // 默认配置
        OBJECT_MAPPER.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    /**
     * 将 Object 转为 Json 字符串
     *
     * @param object 对象
     * @return Json 字符串
     */
    public static <T> String toJson(T object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException error) {
            log.error("convert {} to JSON failure with error {}", object, error);
            return "";
        }
    }

    /**
     * 将 Object 转为 Json 字符串，自定义配置参数
     *
     * @param object 对象
     * @return Json 字符串
     */
    public static <T> String toJson(T object, SerializationConfig config) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setConfig(config);
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException error) {
            log.error("convert {} to JSON failure with error {}", object, error);
            return "";
        }
    }

    /**
     * 将 Json 字符串 转为 Object
     *
     * @param json json 字符串
     * @param type 类型
     * @return 对象
     */
    public static <T> T fromJson(String json, Class<T> type) {
        try {
            return OBJECT_MAPPER.readValue(json, type);
        } catch (IOException error) {
            log.error("convert {} to POJO failure with error {}", json, error);
            return null;
        }
    }

    /**
     * 将 Json 字符串 转为 Object，自定义配置参数
     *
     * @param json json 字符串
     * @param type 类型
     * @return 对象
     */
    public static <T> T fromJson(String json, Class<T> type, DeserializationConfig config) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setConfig(config);
            return objectMapper.readValue(json, type);
        } catch (IOException error) {
            log.error("convert {} to POJO failure with error {}", json, error);
            return null;
        }
    }

    /**
     * 通过引用类型来转换
     *
     * @param json json字符串
     * @return 转化后的对象
     * @author Eric Xue
     * @date 2021/05/09
     */
    public static <T> T fromJson(String json, TypeReference<T> typeReference) {
        try {
            return OBJECT_MAPPER.readValue(json, typeReference);
        } catch (IOException error) {
            log.error("convert {} to POJO failure with error {}", json, error);
            return null;
        }
    }

    /**
     * 通过引用类型来转换，自定义配置文件
     *
     * @param json json字符串
     * @return 转化后的对象
     * @author Eric Xue
     * @date 2021/05/09
     */
    public static <T> T fromJson(
            String json, TypeReference<T> typeReference, DeserializationConfig config) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setConfig(config);
            return objectMapper.readValue(json, typeReference);
        } catch (IOException error) {
            log.error("convert {} to POJO failure with error {}", json, error);
            return null;
        }
    }
}
