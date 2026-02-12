package com.stakely.fluffybarnacle;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

class FluffybarnacleMainTest {
  @Test
  void main_invokes_springApplicationRun() {
    try (MockedStatic<SpringApplication> mocked = Mockito.mockStatic(SpringApplication.class)) {
      ConfigurableApplicationContext ctx = Mockito.mock(ConfigurableApplicationContext.class);
      mocked
          .when(
              () ->
                  SpringApplication.run(
                      Mockito.eq(FluffybarnacleApplication.class), Mockito.any(String[].class)))
          .thenReturn(ctx);

      FluffybarnacleApplication.main(new String[0]);

      mocked.verify(
          () ->
              SpringApplication.run(
                  Mockito.eq(FluffybarnacleApplication.class), Mockito.any(String[].class)));
    }
  }
}
