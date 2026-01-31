package com.stakely.fluffybarnacle.security.logging;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.MDC;

import java.security.Principal;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RequestLoggingInterceptorTest {

  private final RequestLoggingInterceptor interceptor = new RequestLoggingInterceptor();

  @Test
  void preHandle_returnsTrue() {
    HttpServletRequest req = mock(HttpServletRequest.class);
    HttpServletResponse res = mock(HttpServletResponse.class);
    assertTrue(interceptor.preHandle(req, res, new Object()));
    // nothing should be left in MDC
    assertNull(MDC.getCopyOfContextMap());
  }

  @Test
  void afterCompletion_handlesNullCookiesAndNullHeadersAndAnonymousUserAndEmptyResponseHeaders() {
    HttpServletRequest req = mock(HttpServletRequest.class);
    HttpServletResponse res = mock(HttpServletResponse.class);

    when(req.getCookies()).thenReturn(null);
    when(req.getHeaderNames()).thenReturn(null);
    when(req.getRemoteAddr()).thenReturn("127.0.0.1");
    when(req.getRemotePort()).thenReturn(8080);
    when(req.getContentType()).thenReturn("application/json");
    when(req.getMethod()).thenReturn("GET");
    when(req.getRequestURI()).thenReturn("/test");
    when(req.getQueryString()).thenReturn("a=1");
    when(req.getRequestURL()).thenReturn(new StringBuffer("http://localhost/test"));
    when(req.getUserPrincipal()).thenReturn(null);

    when(res.getStatus()).thenReturn(201);
    when(res.getHeaderNames()).thenReturn(Collections.emptySet());

    // pre-populate MDC to ensure interceptor clears it
    MDC.put("to.clear", "value");

    interceptor.afterCompletion(req, res, new Object(), null);

    // MDC must be cleared by the interceptor
    assertNull(MDC.getCopyOfContextMap());
  }

  @Test
  void afterCompletion_handlesCookiesHeadersUserAndResponseHeaders() {
    HttpServletRequest req = mock(HttpServletRequest.class);
    HttpServletResponse res = mock(HttpServletResponse.class);

    Cookie[] cookies = new Cookie[] {new Cookie("session", "abc"), new Cookie("token", "xyz")};
    when(req.getCookies()).thenReturn(cookies);

    Enumeration<String> headerNames = Collections.enumeration(List.of("Accept", "X-Custom"));
    when(req.getHeaderNames()).thenReturn(headerNames);
    when(req.getHeader("Accept")).thenReturn("application/json");
    when(req.getHeader("X-Custom")).thenReturn("value");

    when(req.getRemoteAddr()).thenReturn("192.168.0.1");
    when(req.getRemotePort()).thenReturn(1234);
    when(req.getContentType()).thenReturn("text/plain");
    when(req.getMethod()).thenReturn("POST");
    when(req.getRequestURI()).thenReturn("/submit");
    when(req.getQueryString()).thenReturn(null);
    when(req.getRequestURL()).thenReturn(new StringBuffer("http://localhost/submit"));

    Principal principal = () -> "test-user";
    when(req.getUserPrincipal()).thenReturn(principal);

    when(res.getStatus()).thenReturn(200);
    when(res.getHeaderNames()).thenReturn(List.of("X-Resp", "Set-Cookie"));
    when(res.getHeader("X-Resp")).thenReturn("ok");
    when(res.getHeader("Set-Cookie")).thenReturn("cookie=1");

    // ensure MDC is empty before call
    assertNull(MDC.getCopyOfContextMap());

    interceptor.afterCompletion(req, res, new Object(), null);

    // interceptor must clear MDC at the end
    assertNull(MDC.getCopyOfContextMap());
  }

  @Test
  void afterCompletion_handlesMixOfNullAndNonNullHeaderEnumeration() {
    HttpServletRequest req = mock(HttpServletRequest.class);
    HttpServletResponse res = mock(HttpServletResponse.class);

    when(req.getCookies()).thenReturn(new Cookie[] {new Cookie("only", "1")});
    when(req.getHeaderNames()).thenReturn(Collections.enumeration(List.of()));
    when(req.getRemoteAddr()).thenReturn("10.0.0.1");
    when(req.getRemotePort()).thenReturn(9999);
    when(req.getContentType()).thenReturn(null);
    when(req.getMethod()).thenReturn("DELETE");
    when(req.getRequestURI()).thenReturn("/remove");
    when(req.getQueryString()).thenReturn("");
    when(req.getRequestURL()).thenReturn(new StringBuffer("http://localhost/remove"));
    when(req.getUserPrincipal()).thenReturn(null);

    when(res.getStatus()).thenReturn(404);
    when(res.getHeaderNames()).thenReturn(List.of());

    interceptor.afterCompletion(req, res, new Object(), new RuntimeException("boom"));

    assertNull(MDC.getCopyOfContextMap());
  }
}
