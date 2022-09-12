package edu.cming.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class TaskExecutorConfig {
    private static volatile ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Bean(value = "applicationTaskExecutor")
    public static ThreadPoolTaskExecutor applicationTaskExecutor() {
        if (threadPoolTaskExecutor == null) {
            synchronized (ThreadPoolTaskExecutor.class) {
                if (threadPoolTaskExecutor == null) {
                    threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
                    threadPoolTaskExecutor.setCorePoolSize(4); // 核心数量
                    threadPoolTaskExecutor.setMaxPoolSize(8); // 最大数量
                    threadPoolTaskExecutor.setKeepAliveSeconds(10); // 超时时间：非核心线程存活时间
                    threadPoolTaskExecutor.setThreadNamePrefix("CMINGTask-");
                    threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy()); // 满队列拒绝策略：主线程运行
                    threadPoolTaskExecutor.initialize();
                }
            }
        }
        return threadPoolTaskExecutor;
    }
}
