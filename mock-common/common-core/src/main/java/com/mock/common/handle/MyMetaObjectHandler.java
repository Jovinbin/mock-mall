package com.mock.common.handle;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author: zhao
 * @create: 2022-06-09 18:26
 * @description: 自定义的数据填充handler，分别写insert和update的写入策略
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Object createTime = this.getFieldValByName("createTime", metaObject);
        Object updateTime = this.getFieldValByName("updateTime", metaObject);
        if (Objects.isNull(createTime)) {
            this.strictInsertFill(metaObject, "createTime", () -> LocalDateTime.now(), LocalDateTime.class);
        }
        if (Objects.isNull(updateTime)) {
            this.strictUpdateFill(metaObject, "updateTime", () -> LocalDateTime.now(), LocalDateTime.class);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
//        this.strictUpdateFill(metaObject, "updateTime", () -> LocalDateTime.now(), LocalDateTime.class);
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
    }
}