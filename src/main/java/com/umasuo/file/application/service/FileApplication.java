package com.umasuo.file.application.service;

import com.umasuo.file.domain.model.FileStorage;
import com.umasuo.file.domain.service.FileStorageService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by umasuo on 17/6/15.
 */
@Service
public class FileApplication {

  private final static Logger logger = LoggerFactory.getLogger(FileApplication.class);

  @Autowired
  private FileStorageService fileStorageService;

  @Autowired
  private transient StorageApplication storageApplication;

  public String upload(MultipartFile file, String developerId, String userId)
      throws IOException {
    logger.debug("Enter. developerId: {}, userId: {}.", developerId, userId);

    String id = UUID.randomUUID().toString();

    String publicLink = storageApplication.uploadToAliyun(file, id);

    FileStorage fileStorage = new FileStorage();
    fileStorage.setId(id);
    fileStorage.setFileName(file.getOriginalFilename());
    fileStorage.setDeveloperId(developerId);
    fileStorage.setUserId(userId);
    fileStorage.setFileUrl(publicLink);

    fileStorageService.save(fileStorage);

    logger.debug("Exit.");
    return publicLink;
  }


}
