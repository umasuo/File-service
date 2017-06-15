package com.umasuo.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by umasuo on 17/2/9.
 */
@SpringBootApplication
@RestController
public class Application {

  @Value("${spring.application.name}")
  private String serviceName;

  @GetMapping("/")
  public String getServiceName() {
    return serviceName + " : " + System.currentTimeMillis();
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
