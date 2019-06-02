package com.dotterbear.schedule.a.tweet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@SpringBootApplication
@EnableEncryptableProperties
@EnableScheduling
public class ScheduleATweetApplication {

  public static void main(String[] args) {
    SpringApplication.run(ScheduleATweetApplication.class, args);
  }

}
