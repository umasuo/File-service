package com.umasuo.file.infrastructure.config;

import com.umasuo.file.application.service.AliStorage;
import com.umasuo.file.application.service.GcloudStorage;
import com.umasuo.file.application.service.StorageApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Davis on 17/7/28.
 */
@Configuration
public class StorageFactory {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(StorageFactory.class);

  /**
   * 用来生成不同的StorageApplication的配置项。
   * 目前支持gcloud和ali，分别表示google cloud storage和阿里云OSS
   */
  @Value("${storage}")
  private String storage;

  @Value("${aliyun.storage.endpoint}")
  private String endpoint;

  @Value("${aliyun.storage.accessKeyId}")
  private String accessKeyId;

  @Value("${aliyun.storage.accessKeySecret}")
  private String accessKeySecret;

  @Value("${aliyun.storage.bucket}")
  private String aliBucket;

  @Value("${gcloud.storage.bucket}")
  private String gcloudBucket;

  /**
   * 根据配置生成不同的StorageApplication
   */
  @Bean("${fileStorage}")
  public StorageApplication getIStorage() {
    switch (storage) {
      case "gcloud":
        return new GcloudStorage(gcloudBucket);
      case "ali":
        return new AliStorage(endpoint, accessKeyId, accessKeySecret, aliBucket);
      default:
        return null;
    }
  }
}
