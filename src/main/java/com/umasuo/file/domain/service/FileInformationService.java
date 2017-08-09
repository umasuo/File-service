package com.umasuo.file.domain.service;

import com.umasuo.file.domain.model.FileInformation;
import com.umasuo.file.infrastructure.repository.FileInformationRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by umasuo on 17/6/15.
 */
@Service
public class FileInformationService {

  /**
   * Log.
   */
  private final static Logger LOG = LoggerFactory.getLogger(FileInformationService.class);

  /**
   * FileInformation repository.
   */
  @Autowired
  private transient FileInformationRepository repository;

  /**
   * 保存文件的信息.
   *
   * @param fileStorage 文件信息
   * @return 存储的信息
   */
  public FileInformation save(FileInformation fileStorage) {
    LOG.debug("Enter. fileStorage: {}.", fileStorage);

    FileInformation file = repository.save(fileStorage);

    LOG.debug("Exit. fileStorage: {}.", file);
    return file;
  }
}
