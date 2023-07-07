package com.rookiesquad.excelparsing.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ThreadPoolConfig {

    private static final String THREAD_NAME_PREFIX = "excel_task_pool";

    @Value("${thread.pool.core-pool-size:10}")
    private int corePoolSize;
    @Value("${thread.pool.maximum-pool-size:20}")
    private int maximumPoolSize;
    @Value("${thread.pool.keep-alive-time:3600}")
    private int keepAliveTime;
    @Value("${thread.pool.queue-capacity:10}")
    private int queueCapacity;

    @Bean("threadPoolTaskExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        CustomizableThreadFactory customizableThreadFactory = new CustomizableThreadFactory(THREAD_NAME_PREFIX);
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(corePoolSize);
        threadPoolTaskExecutor.setMaxPoolSize(maximumPoolSize);
        threadPoolTaskExecutor.setKeepAliveSeconds(keepAliveTime);
        threadPoolTaskExecutor.setQueueCapacity(queueCapacity);
        threadPoolTaskExecutor.setThreadFactory(customizableThreadFactory);
        return threadPoolTaskExecutor;
    }

}
