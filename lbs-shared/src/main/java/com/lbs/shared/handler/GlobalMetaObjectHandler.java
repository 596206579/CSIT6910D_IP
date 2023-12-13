package com.lbs.shared.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 自动填充实体时间的字段
 *
 * @author Eric Xue
 * @date 2021/02/08
 * @describe
 * 此类是一个自动填充处理器，它实现了MyBatis Plus的MetaObjectHandler接口，
 * 在MyBatis操作数据库时自动填充或更新特定字段
 */
@Component
public class GlobalMetaObjectHandler implements MetaObjectHandler {
    // 当插入操作发生时自动调用
    @Override
    public void insertFill(MetaObject metaObject) {
        // 当实体类中有createTime字段时，自动填充当前时间
        strictInsertFill(metaObject, "createTime", Date.class, new Date());
        // 当实体类中有updateTime字段时，也自动填充当前时间
        strictInsertFill(metaObject, "updateTime", Date.class, new Date());
    }

    // 当更新操作发生时自动调用
    @Override
    public void updateFill(MetaObject metaObject) {
        // 当实体类中有updateTime字段时，自动更新为当前时间
        strictUpdateFill(metaObject, "updateTime", Date.class, new Date());
    }
}
