package com.umasuo.file.application.service.storage;

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
  private static final Logger LOG = LoggerFactory.getLogger(AliStorage.class);

  /**
   * The endpoint.
   */
  private transient String endpoint;

  /**
   * The access key id.
   */
  private transient String accessKeyId;

  /**
   * The access key secret.
   */
  private transient String accessKeySecret;

  /**
   * The bucket name.
   */
  private transient String bucket;

  /**
   * Instantiates a new AliStorage.
   *
   * @param endpoint the endpoint
   * @param accessKeyId the access key id
   * @param accessKeySecret the access key secret
   * @param bucket the bucket
   */
  public AliStorage(String endpoint, String accessKeyId, String accessKeySecret, String bucket) {
    this.endpoint = endpoint;
    this.accessKeyId = accessKeyId;
    this.accessKeySecret = accessKeySecret;
    this.bucket = bucket;
  }

  /**
   * Upload file to Ali cloud storage.
   *
   * @param file the file
   * @param name the name
   * @return public link for this file
   * @throws IOException exception when upload file to cloud storage bucket
   */
  @Override
  public String upload(MultipartFile file, String name) throws IOException {
    LOG.debug("Enter. file name: {}.", name);

    OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);

    client.putObject(bucket, name, new ByteArrayInputStream(file.getBytes()));

    client.shutdown();

    String publicLink = getPublicLink(bucket, endpoint, name);

    LOG.debug("Exit. public link: {}.", publicLink);

    return publicLink;
  }

  /**
   * Get public link for file by it's name and ali cloud storage bucket name and endpoint.
   *
   * @param bucket the bucket name
   * @param endpoint the endpoint
   * @param name the file name
   * @return public link in format: http://%s.%s/%s
   */
  private static String getPublicLink(String bucket, String endpoint, String name) {
    String publicLinkFormat = "http://%s.%s/%s";
    return String.format(publicLinkFormat, bucket, endpoint, name);
  }
}
