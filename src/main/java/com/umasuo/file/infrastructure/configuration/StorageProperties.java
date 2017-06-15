package com.umasuo.file.infrastructure.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created by umasuo on 17/6/15.
 */
@Configuration
@Data
public class StorageProperties {

  /**
   * Folder location for storing files
   */

  private String location = "upload-dir";

  @Value("${file.root-path: ada}")
  private String url;

}
