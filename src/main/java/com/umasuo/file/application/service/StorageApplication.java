package com.umasuo.file.application.service;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectResult;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Acl.Role;
import com.google.cloud.storage.Acl.User;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Davis on 17/7/17.
 */
@Service
public class StorageApplication {

  /**
   * Logger.
   */
  private static final Logger logger = LoggerFactory.getLogger(StorageApplication.class);

  private static Storage storage = null;

  static {
    storage = StorageOptions.getDefaultInstance().getService();
  }

  @Value("${aliyun.storage.endpoint}")
  private  String endpoint;

  @Value("${aliyun.storage.accessKeyId}")
  private String accessKeyId;

  @Value("${aliyun.storage.accessKeySecret}")
  private String accessKeySecret;

  @Value("${aliyun.storage.bucket}")
  private String bucket;

  public void uploadFile(MultipartFile file, String bucketName, String id) throws IOException {
    logger.debug("Enter.");
    BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, id)
        .setContentType(file.getContentType())
        .setAcl(new ArrayList<>(Arrays.asList(Acl.of(User.ofAllUsers(), Role.READER))))
        .build();

    // the inputstream is closed by default, so we don't need to close it here
    storage.create(blobInfo, file.getInputStream());
    logger.debug("Exit.");
  }

  public String uploadToAliyun(MultipartFile file, String id) throws IOException {
    logger.debug("Enter.");

    OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);

     client.putObject(bucket, id, new ByteArrayInputStream(file.getBytes()));

     client.shutdown();

    return getPublicLink(bucket, endpoint, id);
  }

  public static String getPublicLink(String bucket, String endpoint, String id) {
    String publicLinkFormat = "http://%s.%s/%s";
    return String.format(publicLinkFormat, bucket, endpoint, id);
  }
}
