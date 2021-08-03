package com.starter.config;

import java.util.concurrent.Executor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.task.TaskExecutionProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@Slf4j
@EnableAsync
@EnableScheduling
public class AsyncConfiguration implements AsyncConfigurer {

  private final ApplicationProperties applicationProperties;
  private final TaskExecutionProperties taskExecutionProperties;

  public AsyncConfiguration(
      ApplicationProperties applicationProperties,
      TaskExecutionProperties taskExecutionProperties) {
    this.applicationProperties = applicationProperties;
    this.taskExecutionProperties = taskExecutionProperties;
  }

  private Executor getExecutor(String prefix) {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(taskExecutionProperties.getPool().getCoreSize());
    executor.setMaxPoolSize(taskExecutionProperties.getPool().getMaxSize());
    executor.setQueueCapacity(taskExecutionProperties.getPool().getQueueCapacity());
    executor.setThreadNamePrefix(prefix);
    executor.setAwaitTerminationSeconds(15);
    executor.setWaitForTasksToCompleteOnShutdown(true);

    return executor;
  }
}
