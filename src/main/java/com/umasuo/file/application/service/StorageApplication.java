package com.umasuo.file.application.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by Davis on 17/7/17.
 */
public interface StorageApplication {
  String upload(MultipartFile file, String name) throws IOException;
}
