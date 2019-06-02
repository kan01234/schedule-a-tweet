package com.dotterbear.schedule.a.tweet.db.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.dotterbear.schedule.a.tweet.db.model.UploadFile;

@Repository
public interface UploadFileRepository extends MongoRepository<UploadFile, String> {
}
