package com.umasuo.file.application.service.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by Davis on 17/7/17.
 */
public interface StorageApplication {

  /**
   * Upload file.
   *
   * @param file the file
   * @param name the name
   * @return the string
   * @throws IOException the io exception
   */
  String upload(MultipartFile file, String name) throws IOException;
}
