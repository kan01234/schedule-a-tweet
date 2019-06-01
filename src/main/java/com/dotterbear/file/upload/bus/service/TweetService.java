package com.dotterbear.file.upload.bus.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.dotterbear.file.upload.db.model.ScheduledTweet;
import com.dotterbear.file.upload.db.model.UploadFile;
import com.dotterbear.file.upload.db.service.ScheduledTweetService;
import com.dotterbear.file.upload.db.service.UploadFileService;
import com.dotterbear.file.upload.service.StorageService;
import com.dotterbear.file.upload.utils.DateUtils;
import com.google.protobuf.ByteString;

@Service
public class TweetService {

  private static final Logger log = LoggerFactory.getLogger(TweetService.class);

  @Autowired
  private StorageService storageService;

  @Autowired
  private ScheduledTweetService scheduledTweetService;

  @Autowired
  private UploadFileService uploadFileService;

  @Autowired
  private VisionService visionService;

  public boolean addTweet(ScheduledTweet scheduledTweet) throws Exception {
    if (scheduledTweet == null)
      throw new Exception("scheduledTweet is null");
    scheduledTweetService.save(scheduledTweet);
    return true;
  }

  public UploadFile addMedia(MultipartFile multipartFile) throws Exception {
    if (multipartFile == null || multipartFile.isEmpty())
      throw new Exception("file is null or empty");
    UploadFile uploadFile = storageService.store(multipartFile);
    uploadFile
        .setLabels(visionService.getImageLabels(ByteString.copyFrom(multipartFile.getBytes())));
    return uploadFile;
  }

  public List<ScheduledTweet> findScheduledTweets() {
    // TODO make a query builder class
    return scheduledTweetService
        .findAll(new Query(Criteria.where("tweetStatus").is(ScheduledTweet.INIT))
            .with(Sort.by(Direction.ASC, "scheduledTime")));
  }

  public List<ScheduledTweet> findPostedTweets() {
    // TODO make a query builder class
    return scheduledTweetService
        .findAll(new Query(Criteria.where("tweetStatus").is(ScheduledTweet.DONE))
            .with(Sort.by(Direction.ASC, "createdAt")));
  }

  public Resource getUploadImage(String uploadFileId) {
    UploadFile uploadFile = uploadFileService.findById(uploadFileId);
    if (uploadFile == null)
      return null;
    return storageService.loadAsResource(uploadFile.getFileName());
  }
}
