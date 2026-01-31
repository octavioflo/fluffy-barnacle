package com.stakely.fluffybarnacle.security.logging;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class InterceptorConfigTest {

  @Mock RequestLoggingInterceptor requestLoggingInterceptor;
  @Mock InterceptorRegistry registry;

  @Test
  void addInterceptors_registersProvidedInterceptor() {
    InterceptorConfig config = new InterceptorConfig(requestLoggingInterceptor);
    config.addInterceptors(registry);
    verify(registry, times(1)).addInterceptor(requestLoggingInterceptor);
  }

  @Test
  void addInterceptors_allowsNullInterceptor_andRegistersNull() {
    InterceptorConfig config = new InterceptorConfig(null);
    config.addInterceptors(registry);
    verify(registry, times(1)).addInterceptor((RequestLoggingInterceptor) null);
  }
}
