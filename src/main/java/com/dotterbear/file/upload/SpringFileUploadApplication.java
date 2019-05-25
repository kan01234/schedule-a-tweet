package com.dotterbear.file.upload;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.dotterbear.file.upload.service.StorageService;

@SpringBootApplication
public class SpringFileUploadApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringFileUploadApplication.class, args);
  }

  @Bean
  CommandLineRunner init(StorageService storageService) {
    return (args) -> {
      storageService.deleteAll();
      storageService.init();
    };
  }
}
