package com.umasuo.file.application.rest;

import com.umasuo.file.application.service.StorageApplication;
import com.umasuo.file.infrastructure.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by umasuo on 17/6/15.
 */
@Controller
@CrossOrigin
public class FileController {

  private final static Logger logger = LoggerFactory.getLogger(FileController.class);

  @Autowired
  private transient StorageApplication storageApplication;

  /**
   * 拉取本地存储的文件.
   *
   * @param id          文件ID
   * @param developerId 开发者ID
   * @param userId      用户ID
   * @return Resource
   */
  @GetMapping(Router.FILE_ON_LOCAL_WITH_ID)
  @ResponseBody
  public ResponseEntity<Resource> serveFile(@PathVariable String id,
                                            @RequestHeader(required = false) String developerId,
                                            @RequestHeader(required = false) String userId) {

    logger.info("Enter. id: {}, developerId: {}, userId: {}.", id, developerId, userId);

    Resource file = storageApplication.loadAsResource(id, developerId, userId);

    logger.info("Exit.");
    return ResponseEntity
        .ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename()
            + "\"")
        .body(file);
  }

  /**
   * 拉取存取文件，该文件可能存储在本地，也可能存储在第三方存储服务器.
   *
   * @param id          文件ID
   * @param developerId 开发者ID
   * @param userId      用户ID
   * @return 文件URL
   */
  @GetMapping(Router.FILE_WITH_ID)
  @ResponseBody
  public String fetchFile(@PathVariable String id,
                          @RequestHeader(required = false) String developerId,
                          @RequestHeader(required = false) String userId) {
    logger.info("Enter. id: {}, developerId: {}, userId: {}.", id, developerId, userId);

    String fileUrl = storageApplication.loadAsString(id, developerId, userId);

    logger.info("Exit. fileUrl: {}.", fileUrl);
    return "redirect:" + fileUrl;
  }

  /**
   * 上传文件.
   *
   * @param file        文件
   * @param developerId 开发者ID
   * @param userId      用户ID
   * @return 文件URL
   */
  @PostMapping(Router.FILE_ROOT)
  @ResponseBody
  public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                 @RequestHeader String developerId,
                                 @RequestHeader String userId) {
    logger.info("Enter., developerId: {}, userId: {}.", developerId, userId);

    String fileUrl = storageApplication.store(file, developerId, userId);

    logger.info("Exit. fileUrl: {}.", fileUrl);
    return fileUrl;
  }

}
