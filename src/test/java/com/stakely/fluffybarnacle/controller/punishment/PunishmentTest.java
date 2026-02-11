package com.stakely.fluffybarnacle.controller.punishment;

import com.stakely.fluffybarnacle.controller.PunishmentController;
import com.stakely.fluffybarnacle.dto.punishment.PunishmentRequestDto;
import com.stakely.fluffybarnacle.dto.punishment.PunishmentResponseDto;
import com.stakely.fluffybarnacle.service.PunishmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PunishmentTest {
  private PunishmentController punishmentController;
  @Mock private PunishmentService punishmentService;

  @BeforeEach
  void setUp() {
    MockHttpServletRequest request = new MockHttpServletRequest();
    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    punishmentController = new PunishmentController(punishmentService);
  }

  @Test
  void testGetPunishments() {
    when(punishmentService.getPunishments()).thenReturn(List.of());
    var result = punishmentController.getPunishments();
    assert (result.isEmpty());
  }

  @Test
  void testCreatePunishment() {
    PunishmentRequestDto punishmentRequestDto = new PunishmentRequestDto();
    PunishmentResponseDto punishmentResponseDto =
        new PunishmentResponseDto(UUID.randomUUID(), "type", "details");
    when(punishmentService.createPunishment(punishmentRequestDto))
        .thenReturn(punishmentResponseDto);
    var responseEntity = punishmentController.createPunishment(punishmentRequestDto);
    assert (responseEntity.getStatusCode().is2xxSuccessful());
  }
}
