package com.dotterbear.file.upload.bus.service;

import java.text.ParseException;
import java.util.List;
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

@Service
public class TweetService {

  @Autowired
  private StorageService storageService;

  @Autowired
  private ScheduledTweetService scheduledTweetService;

  @Autowired
  private UploadFileService uploadFileService;

  @Autowired
  private DateUtils dateUtils;

  public boolean addTweet(String tweetText, MultipartFile tweetFile, String tweetDatetime)
      throws ParseException {
    if (tweetFile == null || tweetFile.isEmpty())
      scheduledTweetService
          .save(new ScheduledTweet(tweetText, dateUtils.parseTweetRequestDate(tweetDatetime)));
    else {
      UploadFile uploadFile = storageService.store(tweetFile);
      scheduledTweetService.save(new ScheduledTweet(uploadFile.getId(), tweetText,
          dateUtils.parseTweetRequestDate(tweetDatetime)));
    }
    return true;
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
