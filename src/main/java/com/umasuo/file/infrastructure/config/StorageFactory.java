package com.umasuo.file.infrastructure.config;

import com.umasuo.file.application.service.storage.AliStorage;
import com.umasuo.file.application.service.storage.GoogleStorage;
import com.umasuo.file.application.service.storage.StorageApplication;

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
   * Log.
   */
  private static final Logger LOG = LoggerFactory.getLogger(StorageFactory.class);

  /**
   * 用来生成不同的StorageApplication的配置项。
   * 目前支持gcloud和ali，分别表示google cloud storage和阿里云OSS
   */
  @Value("${storage}")
  private transient String storage;

  /**
   * The endpoint for ali cloud.
   */
  @Value("${aliyun.storage.endpoint}")
  private transient String aliEndpoint;

  /**
   * The access key id for ali cloud.
   */
  @Value("${aliyun.storage.accessKeyId}")
  private transient String aliAccessKeyId;

  /**
   * The access key secret for ali cloud.
   */
  @Value("${aliyun.storage.accessKeySecret}")
  private transient String aliAccessKeySecret;

  /**
   * The bucket name for ali cloud.
   */
  @Value("${aliyun.storage.bucket}")
  private transient String aliBucket;

  /**
   * The bucket name for google cloud.
   */
  @Value("${gcloud.storage.bucket}")
  private transient String gcloudBucket;

  /**
   * 根据配置生成不同的StorageApplication
   */
  @Bean("${fileStorage}")
  public StorageApplication getStorage() {
    StorageApplication storageApplication = null;
    switch (storage) {
      case "gcloud":
        LOG.info("Init Google cloud storage.");
        storageApplication = new GoogleStorage(gcloudBucket);
        break;
      case "ali":
        LOG.info("Init Ali cloud storage.");
        storageApplication =
            new AliStorage(aliEndpoint, aliAccessKeyId, aliAccessKeySecret, aliBucket);
        break;
      default:
        LOG.info("Config wrong, can not init storage application.");
        break;
    }
    return storageApplication;
  }
}
