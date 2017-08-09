package com.umasuo.file.application.service;

import com.umasuo.file.domain.model.FileStorage;
import com.umasuo.file.domain.service.FileStorageService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

import javax.persistence.Transient;

/**
 * Created by umasuo on 17/6/15.
 */
@Service
public class FileApplication {

  /**
   * Log.
   */
  private final static Logger LOG = LoggerFactory.getLogger(FileApplication.class);

  /**
   * FileStorage Service.
   */
  @Autowired
  private transient FileStorageService fileStorageService;

  /**
   * Storage Application, support Ali Cloud and Google Cloud.
   */
  @Autowired
  @Qualifier("${fileStorage}")
  private transient StorageApplication storageApplication;

  /**
   * Upload file to cloud storage bucket, and save it's information into database.
   *
   * @param file the file
   * @param developerId the developer id, required
   * @param userId the user id, optional
   * @return the public link for file
   * @throws IOException exception when upload file to cloud storage bucket
   */
  @Transient
  public String upload(MultipartFile file, String developerId, String userId)
      throws IOException {
    LOG.debug("Enter. developerId: {}, userId: {}.", developerId, userId);

    String id = UUID.randomUUID().toString();

    String publicLink = storageApplication.upload(file, id);

    FileStorage fileStorage = new FileStorage();
    fileStorage.setId(id);
    fileStorage.setFileName(file.getOriginalFilename());
    fileStorage.setDeveloperId(developerId);
    fileStorage.setUserId(userId);
    fileStorage.setFileUrl(publicLink);

    fileStorageService.save(fileStorage);

    LOG.debug("Exit. public link: {}.", publicLink);

    return publicLink;
  }
}
