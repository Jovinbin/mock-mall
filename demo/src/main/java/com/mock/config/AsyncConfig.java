package com.mock.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置类  （可以写多个线程池应用到不同的业务上，但不能实现AsyncConfigurer配置）
 * 如果实现AsyncConfigurer类，则不需要在 @Async注解上加上Bean名称，默认是该线程池 （也可以在该注解后加上Bean名称表示引入指定的线程池配置）
 * @author zhao
 * @since 2022-08-05 15:12
 */
@Slf4j
@EnableAsync    //开启异步
@Configuration
public class AsyncConfig implements AsyncConfigurer {

    /**
     * 设置核心线程数
     */
    private static final int CORE_POOL_SIZE = 10;

    /**
     * 设置最大线程数，只有在缓冲队列满了之后才会申请超过核心线程数的线程
     */
    private static final int MAX_POOL_SIZE = 20;

    /**
     * 设置缓冲队列大小
     */
    private static final int QUEUE_CAPACITY = 200;

    /**
     * 设置线程的最大空闲时间，超过了核心线程数之外的线程，在空闲时间到达之后会被销毁
     */
    private static final int KEEP_ALIVE_SECONDS = 60;

    /**
     * 设置线程名字的前缀，设置好了之后可以方便我们定位处理任务所在的线程池
     */
    private static final String NAME_PREFIX = "Async-service-";

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        // 配置线程池
        ThreadPoolTaskExecutor executor=new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(CORE_POOL_SIZE);
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        executor.setKeepAliveSeconds(KEEP_ALIVE_SECONDS);
        executor.setQueueCapacity(QUEUE_CAPACITY);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setThreadNamePrefix(NAME_PREFIX);
        executor.setAwaitTerminationSeconds(60);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }

    @Override
    public Executor getAsyncExecutor() {
        return taskExecutor();
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
//        return (ex,method,params)->System.out.println(String.format("执行异步方法：%s错误，%s", params, ex));
        return new AsyncExceptionHandler();
    }

    class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

        @Override
        public void handleUncaughtException(Throwable throwable, Method method, Object... objects) {
            // 1、打印异常堆栈
            throwable.printStackTrace();
            // 2、日志记录错误信息
            log.error("AsyncError:{}, Method:{}, Param:{}", throwable.getMessage(), method.getName(), Arrays.asList(objects));
            // 3、TODO 发生异常后通知管理人员（邮件，短信）进一步处理
        }
    }

}
