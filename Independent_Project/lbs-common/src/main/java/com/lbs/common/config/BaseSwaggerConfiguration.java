package com.lbs.common.config;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author Eric Xue
 * @date 2021/05/14
 * @describe:
 * 此类的作用是作为Swagger配置的基础类，为Swagger文档的生成提供必要的配置。
 * 通过动态分析项目中的注解来自动生成API文档，从而简化文档维护工作。
 */

@EnableSwagger2WebMvc
@ConditionalOnProperty(value = "knife4j.enable", havingValue = "true")
public abstract class BaseSwaggerConfiguration implements BeanFactoryAware, InitializingBean {
    // Spring Bean工厂，用于注册Swagger的Docket Bean
    protected DefaultListableBeanFactory beanFactory;
    /** suffix of docket bean name */
    private final String DOCKET_BEAN_NAME_SUFFIX = "Docket";

    // 默认的API分组名称
    private final String DEFAULT_GROUP_NAME = "V1";

    // 在所有属性设置完成后，执行自定义初始化
    @Override
    public void afterPropertiesSet() {
        // 获取所有带有@Api注解的Bean
        Map<String, Object> beansWithAnnotation = beanFactory.getBeansWithAnnotation(Api.class);
        // 分组名称与包名的映射
        Map<String, List<String>> groupNamesWithPackage = new HashMap<>();
        // 遍历带有@Api注解的Bean，进行分组
        beansWithAnnotation.forEach(
                (beanName, clazz) -> {
                    Api api = clazz.getClass().getAnnotation(Api.class);
                    // 获取分组名称，如果没有定义，则使用默认分组名称
                    String groupName =
                            StrUtil.isBlank(api.value()) ? DEFAULT_GROUP_NAME : api.value();
                    groupNamesWithPackage.compute(
                            groupName,
                            (key, value) -> {
                                if (CollectionUtil.isEmpty(value)) {
                                    value = new ArrayList<>();
                                }
                                // 添加包名到对应的分组
                                value.add(clazz.getClass().getPackage().getName());
                                return value;
                            });
                });

        // 为每个分组创建Docket并注册到Spring
        groupNamesWithPackage.forEach(
                (groupName, packages) -> {
                    Docket docket =
                            new Docket(DocumentationType.SWAGGER_2)
                                    .apiInfo(apiInfo())
                                    .groupName(groupName)
                                    .select()
                                    .apis(basePackages(packages))
                                    .paths(PathSelectors.any())
                                    .build();
                    beanFactory.registerSingleton(groupName + DOCKET_BEAN_NAME_SUFFIX, docket);
                });
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    // 构建swagger接口信息参数
    protected abstract ApiInfo apiInfo();

    // 自定义包选择器，用于Swagger扫描多个包
    public Predicate<RequestHandler> basePackages(List<String> packages) {
        return input -> declaringClass(input).map(handlerPackages(packages)).orElse(true);
    }

    // 内部方法，用于判断类是否在指定的包中
    private Function<Class<?>, Boolean> handlerPackages(List<String> packages) {
        return input -> {
            // 遍历包名，判断类的包名是否以其开头
            for (String aPackage : packages) {
                boolean isMatch = input.getPackage().getName().startsWith(aPackage);
                if (isMatch) {
                    return true;
                }
            }
            return false;
        };
    }

    // 获取请求处理器的声明类
    private static Optional<Class<?>> declaringClass(RequestHandler input) {
        return Optional.ofNullable(input.declaringClass());
    }
}
