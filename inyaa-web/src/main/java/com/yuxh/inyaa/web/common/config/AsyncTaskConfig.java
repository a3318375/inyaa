package com.yuxh.inyaa.web.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 异步任务配置
 *
 * @author byteblogs
 * @date 2020/01/21
 */
@Configuration
@EnableAsync
public class AsyncTaskConfig {

    /**
     * 配置线程信息
     */
    @org.springframework.context.annotation.Bean(name = "asyncExecutor")
    public java.util.concurrent.Executor asyncExecutor() {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(3);
        executor.setQueueCapacity(100);
        //拒绝策略:如果执行程序已关闭，则会丢弃该任务
        executor.setRejectedExecutionHandler(new java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy());
        executor.setThreadNamePrefix("HELL0-BLOG-Task-");
        executor.initialize();
        return executor;
    }
}
