package com.umasuo.file.domain.service;

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

  /**
   * Log.
   */
  private final static Logger LOG = LoggerFactory.getLogger(FileStorageService.class);

  /**
   * FileStorage repository.
   */
  @Autowired
  private transient FileStorageRepository repository;

  /**
   * 保存文件的信息.
   *
   * @param fileStorage 文件信息
   * @return 存储的信息
   */
  public FileStorage save(FileStorage fileStorage) {
    LOG.debug("Enter. fileStorage: {}.", fileStorage);

    FileStorage file = repository.save(fileStorage);

    LOG.debug("Exit. fileStorage: {}.", file);
    return file;
  }
}
