package com.mock.doris.task;

import com.mock.doris.service.DorisRoutineLoadStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * @author zhao
 * @since 2022-08-12 11:41
 */
@Slf4j
@Component
public class DorisTask {

    @Resource
    private DorisRoutineLoadStatusService routineLoadStatusService;

    /**
     * 定时任务监控所有 routine load 的运行状态并记录，同时会去清理已经不存在的数据信息
     */
    @Async
    @Scheduled(cron = "0/30 * * * * ?") // 测试用时20秒
    public void monitorDorisRoutineLoad(){
        log.info("定时更新例行导入任务信息");
        routineLoadStatusService.monitorDorisRoutineLoad();
    }

}
