package com.mock.task;

import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.Executor;

/**
 * 定时任务与线程池案例
 * @author zhao
 * @since 2022-08-05 15:08
 */
@Component
public class TaskAsyncExample {

    @Resource(name = "taskExecutor")
    private Executor taskExecutor;

    @Async
    @Scheduled(cron = "0/10 * * * * ?")
    public void test() throws InterruptedException {
        Integer num = 0;
        // 作为该线程是否结束的标记
        boolean flag = true;
        while (flag){
            System.out.println("当前线程名称： " + Thread.currentThread().getName() + " -- " + num);
            Thread.sleep(8000);
            num ++;
            if (num == 5){
                flag = false;
                System.out.println(Thread.currentThread().getName() + " --- 线程结束");
            }
        }
    }

    @Async
//    @Scheduled(cron = "0/50 * * * * ?")
    public void test01() throws InterruptedException {
        System.out.println(" ===== " + Thread.currentThread().getName());

        Runnable runnable = new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                Integer i = 1;
                boolean flag = true;
                while (flag){
                    Thread thread = Thread.currentThread();
                    System.out.println("name = " + thread.getName());
                    System.out.println(" ---- " + i);
                    i+=1;
                    Integer a = Integer.parseInt("a");
                    Thread.sleep(10000);
                    if (i == 3){
                        flag = false;
                    }
                }
            }
        };
        try {
        taskExecutor.execute(runnable);

        //规律重启线程
//        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
//        ScheduledFuture<?> scheduleTask = scheduler.scheduleWithFixedDelay(runnable, 1, 3, TimeUnit.SECONDS);
//        scheduleTask.cancel(true);

        } catch (Exception e) {
//            taskExecutor.execute();
            e.printStackTrace();
        }

    }

}
