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
public class GoogleStorage implements StorageApplication {

  /**
   * Log.
   */
  private static final Logger LOG = LoggerFactory.getLogger(GoogleStorage.class);

  /**
   * The google cloud storage.
   */
  private static Storage storage = StorageOptions.getDefaultInstance().getService();

  /**
   * The bucket name.
   */
  private transient String bucket;

  /**
   * Instantiates a new Google storage.
   *
   * @param bucket the bucket name
   */
  public GoogleStorage(String bucket) {
    this.bucket = bucket;
  }

  /**
   * Upload file to google cloud storage.
   *
   * @param file the file
   * @param name the name
   * @return public link for this file
   * @throws IOException exception when upload file
   */
  @Override
  public String upload(MultipartFile file, String name) throws IOException {
    LOG.debug("Enter. file name: {}.", name);

    BlobInfo blobInfo = BlobInfo.newBuilder(bucket, name)
        .setContentType(file.getContentType())
        .setAcl(new ArrayList<>(Arrays.asList(Acl.of(User.ofAllUsers(), Role.READER))))
        .build();

    // the inputstream is closed by default, so we don't need to close it here
    storage.create(blobInfo, file.getInputStream());

    String publicLink = getPublicLink(bucket, name);

    LOG.debug("Exit. public link: {}.", publicLink);

    return publicLink;
  }

  /**
   * Get public link for file by it's name and ali cloud storage bucket name and endpoint.
   *
   * @param bucket the bucket name
   * @param name the file name
   * @return public link in format: https://storage.googleapis.com/%s/%s
   */
  private String getPublicLink(String bucket, String name) {
    String publicLinkFormat = "https://storage.googleapis.com/%s/%s";
    return String.format(publicLinkFormat, bucket, name);
  }
}
