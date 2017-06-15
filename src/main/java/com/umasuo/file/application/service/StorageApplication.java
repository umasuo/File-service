package com.umasuo.file.application.service;

import com.umasuo.exception.ParametersException;
import com.umasuo.file.domain.model.FileStorage;
import com.umasuo.file.domain.service.FileStorageService;
import com.umasuo.file.infrastructure.Router;
import com.umasuo.file.infrastructure.configuration.StorageProperties;
import com.umasuo.file.infrastructure.exception.StorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * Created by umasuo on 17/6/15.
 */
@Service
public class StorageApplication {

  private final static Logger logger = LoggerFactory.getLogger(StorageApplication.class);

  private final Path rootLocation;

  @Autowired
  private FileStorageService fileStorageService;

  private transient StorageProperties storageProperties;

  @Autowired
  public StorageApplication(StorageProperties storageProperties) {
    this.storageProperties = storageProperties;
    this.rootLocation = Paths.get(storageProperties.getLocation());
  }

  /**
   * 存储上传的文件.
   *
   * @param file        file
   * @param developerId developerId
   * @param userId      userId
   * @return fileId
   */
  public String store(MultipartFile file, String developerId, String userId) {
    logger.debug("Enter. developerId: {}, userId: {}.", developerId, userId);
    try {
      if (file.isEmpty()) {
        throw new ParametersException("File is empty.");
      }
      String fileId = UUID.randomUUID().toString();
      Files.copy(file.getInputStream(), this.rootLocation.resolve(fileId));
      String url = storageProperties + Router.FILE_ON_LOCAL + fileId;

      FileStorage fileStorage = new FileStorage();
      fileStorage.setId(fileId);
      fileStorage.setFileName(file.getOriginalFilename());
      fileStorage.setDeveloperId(developerId);
      fileStorage.setUserId(userId);
      fileStorage.setFileUrl(url);
      fileStorageService.save(fileStorage);

      logger.debug("Exit. fileId: {}.", fileId);
      return fileId;
    } catch (IOException e) {
      logger.debug("Failed to store file.");
      throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
    }
  }

  /**
   * 加载文件，并将文件返回回去.
   *
   * @param id          文件ID
   * @param developerId 开发者ID
   * @param userId      用户ID
   * @return resource
   */
  public Resource loadAsResource(String id, String developerId, String userId) {
    logger.debug("Enter. id: {}, developerId: {}, userId: {}.", id, developerId, userId);
    try {
      Path file = rootLocation.resolve(id);
      Resource resource = new UrlResource(file.toUri());
      if (resource.exists() || resource.isReadable()) {
        logger.debug("Exit.");
        return resource;
      } else {
        logger.debug("Could not read file id: " + id);
        throw new StorageException("Could not read file: " + id);
      }
    } catch (MalformedURLException e) {
      logger.debug("Could not read file id: " + id);
      throw new StorageException("Could not read file: " + id, e);
    }
  }


  /**
   * 加载文件.
   *
   * @param id          文件ID
   * @param developerId 开发者ID
   * @param userId      用户ID
   * @return url.
   */
  public String loadAsString(String id, String developerId, String userId) {
    FileStorage fileStorage = fileStorageService.get(id, developerId, userId);

    return fileStorage.getFileUrl();
  }
}
