package com.stakely.fluffybarnacle.security.logging;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Enumeration;

@Component
public class RequestLoggingInterceptor implements HandlerInterceptor {

  private static final Logger log = LoggerFactory.getLogger(RequestLoggingInterceptor.class);

  @Override
  public boolean preHandle(
      HttpServletRequest request, HttpServletResponse response, Object handler) {
    return true;
  }

  @Override
  public void afterCompletion(
      HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    try {

      // Request information
      Cookie[] cookies = request.getCookies();
      if (cookies != null) {
        for (Cookie cookie : request.getCookies()) {
          MDC.put("request.cookie." + cookie.getName(), cookie.getValue());
        }
      }

      Enumeration<String> headers = request.getHeaderNames();
      if (headers != null) {
        while (headers.hasMoreElements()) {
          String headerName = headers.nextElement();
          MDC.put("request.header." + headerName.toLowerCase(), request.getHeader(headerName));
        }
      }

      MDC.put("request.dest", request.getRemoteAddr());
      MDC.put("request.dest_port", String.valueOf(request.getRemotePort()));
      MDC.put("request.http_content_type", request.getContentType());
      MDC.put("request.http_method", request.getMethod());
      MDC.put("request.src", request.getRemoteAddr());
      MDC.put("request.uri_path", request.getRequestURI());
      MDC.put("request.uri_query", request.getQueryString());
      MDC.put("request.url", request.getRequestURL().toString());
      String user =
          (request.getUserPrincipal() != null) ? request.getUserPrincipal().getName() : "anonymous";
      MDC.put("request.user", user);

      // Response information
      MDC.put("response.status", String.valueOf(response.getStatus()));

      for (String name : response.getHeaderNames()) {
        MDC.put("response.header." + name.toLowerCase(), response.getHeader(name));
      }

      log.info(
          "Completed request: {} {} -> {}",
          request.getMethod(),
          request.getRequestURI(),
          response.getStatus());
    } finally {
      MDC.clear();
    }
    MDC.clear();
  }
}
