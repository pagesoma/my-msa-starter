package com.starter.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

  private RestTemplateConfig restTemplate = new RestTemplateConfig();

  private Integer asyncThreadPoolCount = 4;

  private EventPublisher eventPublisher = new EventPublisher();

  @Getter
  @Setter
  public static class RestTemplateConfig {

    private Integer connectionTimeout = 2000;
    private Integer readTimeout = 5000;
  }

  @Getter
  @Setter
  public static class EventPublisher {

    private Boolean enabled = false;
  }


}
