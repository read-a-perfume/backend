package io.perfume.api.file.adapter.out.persistence.s3;

import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.file.application.exception.SaveFileNotFoundException;
import io.perfume.api.file.application.port.out.S3Repository;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

@PersistenceAdapter
@RequiredArgsConstructor
public class S3PersistenceAdapter implements S3Repository {
  @Value("${aws.s3.bucket}")
  private String bucketName;

  @Value("${aws.s3.cloudFrontPath}")
  private String cloudFrontPath;

  private final S3Client s3Client;

  @Override
  public String uploadFile(MultipartFile file) {
    String key = file.getOriginalFilename();
    PutObjectRequest putObjectRequest =
        PutObjectRequest.builder()
            .bucket(bucketName)
            .key(key)
            .contentType(file.getContentType())
            .contentLength(file.getSize())
            .build();

    byte[] fileContent = getFileContent(file);

    PutObjectResponse response =
        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(fileContent));

    if (!response.sdkHttpResponse().statusText().orElse("FAIL").equals("OK")) {
      throw new IllegalStateException("Failed to upload file to S3.");
    }

    return cloudFrontPath + file.getOriginalFilename();
  }

  private byte[] getFileContent(MultipartFile file) {
    try {
      return file.getBytes();
    } catch (IOException e) {
      throw new SaveFileNotFoundException(e.getMessage());
    }
  }
}
