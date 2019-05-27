package com.dotterbear.file.upload.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import com.dotterbear.file.upload.db.model.UploadFile;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

  void init();

  UploadFile store(MultipartFile file);

  Stream<Path> loadAll();

  Path load(String filename);

  Resource loadAsResource(String filename);

  void deleteAll();

}
