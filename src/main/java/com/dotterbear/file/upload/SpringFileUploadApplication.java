package com.dotterbear.file.upload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@SpringBootApplication
@EnableEncryptableProperties
public class SpringFileUploadApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringFileUploadApplication.class, args);
  }

}
