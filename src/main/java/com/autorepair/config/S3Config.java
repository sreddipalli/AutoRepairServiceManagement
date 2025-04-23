package com.autorepair.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {

  @Value("${s3.bucket.accessKeyId}")
  private String accessKeyId;

  @Value("${s3.bucket.secretAccessKey}")
  private String secretAccessKey;

  @Bean
  public S3Client s3Client() {
    AwsBasicCredentials cred = AwsBasicCredentials.create(accessKeyId, secretAccessKey);

    AwsCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(cred);

    return S3Client.builder().region(Region.US_EAST_1)

        .credentialsProvider(credentialsProvider).build();
  }

}

