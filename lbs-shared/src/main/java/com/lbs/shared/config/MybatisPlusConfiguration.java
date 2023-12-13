package com.lbs.shared.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Eric Xue
 * @date 2021/05/20
 * @describe
 * 此类为配置类
 */
@Configuration
@MapperScan("com.lbs.shared.mapper")
public class MybatisPlusConfiguration {
    // 定义一个Spring Bean，用于配置Mybatis Plus的拦截器
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        // 定义一个Spring Bean，用于配置Mybatis Plus的拦截器
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 向拦截器中添加一个分页的内部拦截器，指定数据库类型为MySQL
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}
