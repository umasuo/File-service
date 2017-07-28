package com.umasuo.file.application.service;

import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Acl.Role;
import com.google.cloud.storage.Acl.User;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Davis on 17/7/28.
 */
public class GcloudStorage implements StorageApplication {

  /**
   * Logger.
   */
  private static final Logger logger = LoggerFactory.getLogger(GcloudStorage.class);

  private static Storage storage = null;

  static {
    storage = StorageOptions.getDefaultInstance().getService();
  }

  private String bucket;

  public GcloudStorage(String bucket) {
    this.bucket = bucket;
  }

  @Override
  public String upload(MultipartFile file, String name) throws IOException {
    logger.debug("Enter.");
    BlobInfo blobInfo = BlobInfo.newBuilder(bucket, name)
        .setContentType(file.getContentType())
        .setAcl(new ArrayList<>(Arrays.asList(Acl.of(User.ofAllUsers(), Role.READER))))
        .build();

    // the inputstream is closed by default, so we don't need to close it here
    storage.create(blobInfo, file.getInputStream());
    logger.debug("Exit.");
    return getPublicLink(bucket, name);
  }

  private String getPublicLink(String bucket, String name) {
    String publicLinkFormat = "https://storage.googleapis.com/%s/%s";
    return String.format(publicLinkFormat, bucket, name);
  }
}
