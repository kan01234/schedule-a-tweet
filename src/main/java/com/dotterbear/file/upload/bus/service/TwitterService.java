package com.dotterbear.file.upload.bus.service;

import java.io.File;
import java.io.IOException;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dotterbear.file.upload.db.model.ScheduledTweet;
import com.dotterbear.file.upload.db.model.UploadFile;
import com.dotterbear.file.upload.db.service.UploadFileService;
import com.dotterbear.file.upload.service.StorageService;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;

@Service
public class TwitterService {

  private static final Logger log = LoggerFactory.getLogger(TwitterService.class);

  @Autowired
  private Twitter twitter;

  @Autowired
  private StorageService storageService;

  @Autowired
  private UploadFileService uploadFileService;

  public Status postTweet(ScheduledTweet scheduledTweet) throws TwitterException, IOException {
    log.debug("postTweet, scheduledTweet: {}", scheduledTweet);
    StringBuilder sb = new StringBuilder();
    sb.append(scheduledTweet.getTweetText());
    if (sb.length() > 0)
      sb.append("\n");
    sb.append(String.join(" ",
        scheduledTweet.getTags().stream().map(tag -> '#' + tag).collect(Collectors.toList())));
    StatusUpdate statusUpdate = new StatusUpdate(sb.toString());
    if (scheduledTweet.getUploadFileId() != null) {
      UploadFile uploadFile = uploadFileService.findById(scheduledTweet.getUploadFileId());
      File media = storageService.loadAsResource(uploadFile.getFileName()).getFile();
      statusUpdate.setMedia(media);
    }
    Status status = twitter.updateStatus(statusUpdate);
    log.debug("status: {}", status);
    scheduledTweet.setTwitterUserId(status.getUser().getId());
    scheduledTweet.setTwitterStatusId(status.getId());
    scheduledTweet.setCreatedAt(status.getCreatedAt());
    return status;
  }

}
