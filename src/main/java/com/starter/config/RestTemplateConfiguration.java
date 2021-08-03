package com.starter.config;

import java.net.URI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@EnableRetry
@Configuration
public class RestTemplateConfiguration {

  @Autowired
  private ApplicationProperties applicationProperties;
//  @Autowired private PushExternalRequestResponseLoggingInterceptor requestLoggingInterceptor;

  private ClientHttpRequestFactory getRequestFactory(int connectionTimeout, int readTimeout) {
    HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();

    factory.setReadTimeout(readTimeout);
    factory.setConnectTimeout(connectionTimeout);
    factory.setConnectionRequestTimeout(connectionTimeout);
    return factory;
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate(
        getRequestFactory(
            applicationProperties.getRestTemplate().getConnectionTimeout(),
            applicationProperties.getRestTemplate().getReadTimeout()));
  }

  @Bean
  public RestTemplate retryableRestTemplate() {
    SimpleClientHttpRequestFactory clientHttpRequestFactory =
        new SimpleClientHttpRequestFactory(); // 1
    clientHttpRequestFactory.setReadTimeout(
        applicationProperties.getRestTemplate().getReadTimeout());
    clientHttpRequestFactory.setConnectTimeout(
        applicationProperties.getRestTemplate().getConnectionTimeout());

    return new RestTemplate(clientHttpRequestFactory) {
      @Override
      @Retryable(
          value = RestClientException.class,
          maxAttempts = 3,
          backoff = @Backoff(delay = 1000)) // 2
      public <T> ResponseEntity<T> exchange(
          URI url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType)
          throws RestClientException {
        return super.exchange(url, method, requestEntity, responseType);
      }

      @Recover
      public <T> ResponseEntity<String> exchangeRecover(RestClientException e) {
        return ResponseEntity.badRequest().body("bad request T.T"); // 3
      }
    };
  }

}
