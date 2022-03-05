package com.xWash.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class CheckThreadPoolExecutor {
    @Bean("checkPool")
    public Executor asyncServiceExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(12);//配置核心线程数
        executor.setMaxPoolSize(12 * 3);//配置最大线程数
        executor.setKeepAliveSeconds(10);
        executor.setQueueCapacity(36 * 3);//配置队列大小
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());//拒绝策略
        executor.setWaitForTasksToCompleteOnShutdown(false);
        executor.initialize();//执行初始化
        return executor;
    }
}
