package io.perfume.api.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Configuration {
  @Value("${aws.s3.access-key}")
  private String accessKey;

  @Value("${aws.s3.private-key}")
  private String privateKey;

  @Bean
  public S3Client amazonS3() {
    AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, privateKey);

    return S3Client.builder()
        .region(Region.AP_NORTHEAST_2)
        .credentialsProvider(StaticCredentialsProvider.create(credentials))
        .build();
  }
}
