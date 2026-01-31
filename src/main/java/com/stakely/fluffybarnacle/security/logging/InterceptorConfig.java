package com.stakely.fluffybarnacle.security.logging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

  private final RequestLoggingInterceptor requestLoggingInterceptor;

  @Autowired
  public InterceptorConfig(RequestLoggingInterceptor requestLoggingInterceptor) {
    this.requestLoggingInterceptor = requestLoggingInterceptor;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(requestLoggingInterceptor);
  }
}
