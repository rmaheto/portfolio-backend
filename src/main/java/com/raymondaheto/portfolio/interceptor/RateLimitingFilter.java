package com.raymondaheto.portfolio.interceptor;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RateLimitingFilter implements Filter {

  private final ConcurrentHashMap<String, Bucket> buckets = new ConcurrentHashMap<>();

  private static final Set<String> RATE_LIMITED_ENDPOINTS =
      Set.of(
          "/registration/register-school",
          "/schools/school-types",
          "/auth/send-signup-verification-code",
          "/auth/verify-email-change-code");

  @Override
  public void doFilter(
      final ServletRequest request, final ServletResponse response, final FilterChain chain)
      throws IOException, ServletException {

    final HttpServletRequest httpReq = (HttpServletRequest) request;
    final String path = httpReq.getRequestURI();

    if (RATE_LIMITED_ENDPOINTS.contains(path)) {
      final String clientIp = request.getRemoteAddr();
      final Bucket bucket = buckets.computeIfAbsent(clientIp, k -> createNewBucket());

      if (bucket.tryConsume(1)) {
        chain.doFilter(request, response);
      } else {
        log.warn("Rate limit exceeded for IP: {}", clientIp);
        final HttpServletResponse httpResp = (HttpServletResponse) response;
        httpResp.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        httpResp.setContentType("application/json");

        final String json =
            """
        {
          "errors": [
            {
              "code": "RATE_LIMIT_EXCEEDED",
              "locale": "eng-USA",
              "localizedMessage": "Too many requests. Please try again later.",
              "severity": "ERROR"
            }
          ]
        }
        """;

        httpResp.getWriter().write(json);
        httpResp.getWriter().flush();
      }
    } else {
      chain.doFilter(request, response);
    }
  }

  private Bucket createNewBucket() {
    final Bandwidth limit = Bandwidth.classic(5, Refill.greedy(5, Duration.ofMinutes(1)));

    return Bucket.builder().addLimit(limit).build();
  }
}
