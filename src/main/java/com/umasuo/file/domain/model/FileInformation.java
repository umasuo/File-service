package com.umasuo.file.domain.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by umasuo on 17/6/15.
 */
@Entity
@Table(name = "file_information")
@Data
public class FileInformation {

  /**
   * The id.
   */
  @Id
  private String id;

  /**
   * The file name.
   */
  private String fileName;

  /**
   * The user id, optional.
   */
  private String userId;

  /**
   * The developer id, required.
   */
  private String developerId;

  /**
   * 文件的路径，如果采用外部存储，例如S3，那么则保存其生成的URL.
   */
  private String fileUrl;

  /**
   * Build file information.
   *
   * @param originalFilename the original filename
   * @param developerId the developer id
   * @param userId the user id
   * @param id the id
   * @param publicLink the public link
   * @return file information
   */
  public static FileInformation build(String originalFilename, String developerId, String userId,
      String id, String publicLink) {
    FileInformation fileStorage = new FileInformation();
    fileStorage.setId(id);
    fileStorage.setFileName(originalFilename);
    fileStorage.setDeveloperId(developerId);
    fileStorage.setUserId(userId);
    fileStorage.setFileUrl(publicLink);
    return fileStorage;
  }
}
