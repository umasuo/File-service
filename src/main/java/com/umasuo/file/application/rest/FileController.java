package com.umasuo.file.application.rest;


import com.umasuo.file.application.service.FileApplication;
import com.umasuo.file.infrastructure.Router;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by umasuo on 17/6/15.
 */
@RestController
@CrossOrigin
public class FileController {

  private final static Logger logger = LoggerFactory.getLogger(FileController.class);

  @Autowired
  private transient FileApplication fileApplication;

  @PostMapping(Router.FILE_ROOT)
  public String upload(@RequestParam MultipartFile file, @RequestHeader String developerId,
      @RequestHeader(required = false) String userId)
      throws IOException {
    logger.info("Enter. developerId: {}, userId: {}.", developerId, userId);

    String publicLink = fileApplication.upload(file, developerId, userId);

    logger.info("Exit. publicLink: {}.", publicLink);

    return publicLink;
  }
}
