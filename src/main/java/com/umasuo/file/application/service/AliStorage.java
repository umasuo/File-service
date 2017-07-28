package com.umasuo.file.application.service;

import com.aliyun.oss.OSSClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created by Davis on 17/7/28.
 */
public class AliStorage implements StorageApplication {

  /**
   * Logger.
   */
  private static final Logger logger = LoggerFactory.getLogger(AliStorage.class);

  private String endpoint;

  private String accessKeyId;

  private String accessKeySecret;

  private String bucket;

  public AliStorage(String endpoint, String accessKeyId, String accessKeySecret,
      String bucket) {
    this.endpoint = endpoint;
    this.accessKeyId = accessKeyId;
    this.accessKeySecret = accessKeySecret;
    this.bucket = bucket;
  }

  public String upload(MultipartFile file, String name) throws IOException {
    logger.debug("Enter.");

    OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);

    client.putObject(bucket, name, new ByteArrayInputStream(file.getBytes()));

    client.shutdown();

    return getPublicLink(bucket, endpoint, name);
  }

  public static String getPublicLink(String bucket, String endpoint, String name) {
    String publicLinkFormat = "http://%s.%s/%s";
    return String.format(publicLinkFormat, bucket, endpoint, name);
  }
}
