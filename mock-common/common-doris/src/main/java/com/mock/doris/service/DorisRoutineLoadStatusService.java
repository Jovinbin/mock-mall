package com.mock.doris.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mock.doris.entity.DorisRoutineLoadStatusEntity;

/**
 * @author zhao
 * @since 2022-08-12 13:45
 */
public interface DorisRoutineLoadStatusService extends IService<DorisRoutineLoadStatusEntity> {

    void monitorDorisRoutineLoad();

}
