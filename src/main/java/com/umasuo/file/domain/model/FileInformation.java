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
}
