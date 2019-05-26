package com.dotterbear.file.upload.db.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.dotterbear.file.upload.db.model.UploadFile;

@Repository
public interface UploadFileRepository extends MongoRepository<UploadFile, String> {
}
