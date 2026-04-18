package com.raymondaheto.portfolio.service;

import com.raymondaheto.portfolio.repository.PortfolioProfileRepository;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@RequiredArgsConstructor
public class AvatarService {

  private static final long MAX_BYTES = 2 * 1024 * 1024;
  private static final String AVATAR_KEY = "avatars/avatar";
  private static final String HTTPS = "https://";

  private final S3Client s3Client;
  private final PortfolioProfileRepository profileRepo;

  @Value("${app.aws.bucket}")
  private String bucket;

  @Value("${AWS_CLOUDFRONT_BASE_URL:}")
  private String cloudfrontBaseUrl;

  public String upload(final MultipartFile file) throws IOException {
    if (file.getSize() > MAX_BYTES) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Image must be 2 MB or smaller.");
    }

    s3Client.putObject(
        PutObjectRequest.builder()
            .bucket(bucket)
            .key(AVATAR_KEY)
            .contentType(file.getContentType())
            .cacheControl("no-cache")
            .build(),
        RequestBody.fromBytes(file.getBytes()));

    final String base = resolveBaseUrl();
    final String url = base + "/" + AVATAR_KEY + "?v=" + System.currentTimeMillis();

    profileRepo.findAll().stream()
        .findFirst()
        .ifPresent(
            p -> {
              p.setAvatarUrl(url);
              profileRepo.save(p);
            });

    return url;
  }

  private String resolveBaseUrl() {
    if (cloudfrontBaseUrl == null || cloudfrontBaseUrl.isBlank()) {
      return HTTPS + bucket + ".s3.amazonaws.com";
    }
    final String withScheme =
        cloudfrontBaseUrl.startsWith(HTTPS) ? cloudfrontBaseUrl : HTTPS + cloudfrontBaseUrl;
    return withScheme.replaceAll("/+$", "");
  }
}
