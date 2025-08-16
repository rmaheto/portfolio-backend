package com.raymondaheto.portfolio.interceptor;

import jakarta.annotation.Nonnull;
import java.io.IOException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

@Component
public class OutboundRequestInterceptor implements ClientHttpRequestInterceptor {

  @Override
  public ClientHttpResponse intercept(
      final HttpRequest request,@Nonnull final byte[] body, final ClientHttpRequestExecution execution)
      throws IOException {
    final HttpHeaders headers = request.getHeaders();

    generateRequestHeaders(headers);
    return execution.execute(request, body);
  }

  private void generateRequestHeaders(final HttpHeaders headers) {
    if (!headers.containsKey(HttpHeaders.CONTENT_TYPE)) {
      headers.setContentType(MediaType.APPLICATION_JSON);
    }
  }
}
