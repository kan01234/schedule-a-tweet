package com.dotterbear.schedule.a.tweet.bus.service;

import java.util.List;
import java.util.stream.Collectors;
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
import com.dotterbear.schedule.a.tweet.db.model.ScheduledTweet;
import com.dotterbear.schedule.a.tweet.db.model.UploadFile;
import com.dotterbear.schedule.a.tweet.db.service.ScheduledTweetService;
import com.dotterbear.schedule.a.tweet.db.service.UploadFileService;
import com.dotterbear.schedule.a.tweet.service.StorageService;
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
    String uploadFileId = scheduledTweet.getUploadFileId();
    if (uploadFileId != null && uploadFileId.trim().isEmpty())
      scheduledTweet.setUploadFileId(null);
    scheduledTweetService.save(scheduledTweet);
    return true;
  }

  public UploadFile addMedia(MultipartFile multipartFile) throws Exception {
    if (multipartFile == null || multipartFile.isEmpty())
      throw new Exception("file is null or empty");
    UploadFile uploadFile = new UploadFile(storageService.store(multipartFile));
    uploadFile.setTags(visionService.getWebEntities(ByteString.copyFrom(multipartFile.getBytes()))
        .stream().map(annotation -> annotation.getDescription()).collect(Collectors.toList()));
    uploadFileService.save(uploadFile);
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
            .with(Sort.by(Direction.DESC, "createdAt")));
  }

  public Resource getUploadImage(String uploadFileId) {
    UploadFile uploadFile = uploadFileService.findById(uploadFileId);
    if (uploadFile == null)
      return null;
    return storageService.loadAsResource(uploadFile.getFileName());
  }

}
