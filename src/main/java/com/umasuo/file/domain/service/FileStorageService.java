package com.umasuo.file.domain.service;

import com.umasuo.exception.NotExistException;
import com.umasuo.file.domain.model.FileStorage;
import com.umasuo.file.infrastructure.repository.FileStorageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by umasuo on 17/6/15.
 */
@Service
public class FileStorageService {

  private final static Logger logger = LoggerFactory.getLogger(FileStorageService.class);

  @Autowired
  private transient FileStorageRepository repository;

  /**
   * 保存文件的关系.
   * @param fileStorage 文件
   * @return 存储的文件
   */
  public FileStorage save(FileStorage fileStorage) {
    logger.debug("Enter. fileStorage: {}.", fileStorage);

    FileStorage file = repository.save(fileStorage);

    logger.debug("Exit. fileStorage: {}.", file);
    return file;
  }

  /**
   * 获取一个文件
   * @param id
   * @return
   */
  public FileStorage get(String id) {
    logger.debug("Enter. id: {}.", id);

    FileStorage file = repository.findOne(id);
    if (file == null) {
      throw new NotExistException("File not exist id: " + id);
    }

    logger.debug("Exit. fileStorage: {}.", file);
    return file;
  }

  /**
   * 获取某个开发者下某个用户上传的文件
   * @param id
   * @param developerId
   * @param userId
   * @return
   */
  public FileStorage get(String id, String developerId, String userId) {
    logger.debug("Enter. id: {}, developerId: {}, userId: {}.", id, developerId, userId);

    FileStorage file = get(id);
    if (!file.getDeveloperId().equals(developerId) || !file.getUserId().equals
        (userId)) {
      throw new NotExistException("File not exist id: " + id + ", developerId: " + developerId +
          ", userId: " + userId);
    }

    logger.debug("Exit. fileStorage: {}.", file);
    return file;
  }

}
