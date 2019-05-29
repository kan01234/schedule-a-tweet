package com.dotterbear.file.upload.bus.service;

import java.text.ParseException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
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
      scheduledTweetService.save(new ScheduledTweet(uploadFile.getId(), uploadFile.getFileName(),
          tweetText, dateUtils.parseTweetRequestDate(tweetDatetime)));
    }
    return true;
  }

  public List<ScheduledTweet> findScheduledTweet() {
    return scheduledTweetService.findAllByTweetStatus(ScheduledTweet.INIT);
  }

  public Resource getUploadImage(String uploadFileId) {
    UploadFile uploadFile = uploadFileService.findById(uploadFileId);
    if (uploadFile == null)
      return null;
    return storageService.loadAsResource(uploadFile.getFileName());
  }
}
