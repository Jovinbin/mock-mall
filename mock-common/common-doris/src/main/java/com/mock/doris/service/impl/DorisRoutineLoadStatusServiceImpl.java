package com.mock.doris.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mock.doris.entity.DorisRoutineLoadStatusEntity;
import com.mock.doris.mapper.DorisRoutineLoadStatusMapper;
import com.mock.doris.service.DorisRoutineLoadStatusService;
import com.mock.doris.util.DorisUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhao
 * @since 2022-08-12 13:45
 */
@Service
public class DorisRoutineLoadStatusServiceImpl extends ServiceImpl<DorisRoutineLoadStatusMapper, DorisRoutineLoadStatusEntity> implements DorisRoutineLoadStatusService {

    @Override
    public void monitorDorisRoutineLoad() {
        // 查询已经入库的导入任务信息
        List<DorisRoutineLoadStatusEntity> list = list();
        List<String> idList = list.stream().map(DorisRoutineLoadStatusEntity::getId).collect(Collectors.toList());
        // 获取当前所有存在的例行导入任务
        List<DorisRoutineLoadStatusEntity> allRoutineLoad = DorisUtil.getAllRoutineLoad();
        // 去除当前存在的导入任务，剩下的为不存在的任务信息，及时进行清理
        idList.removeAll(allRoutineLoad.stream().map(DorisRoutineLoadStatusEntity::getId).collect(Collectors.toList()));
        if (CollectionUtils.isNotEmpty(idList)){
            // 清理已经不存在的任务信息
            removeByIds(idList);
        }
        saveOrUpdateBatch(allRoutineLoad);
    }
}
