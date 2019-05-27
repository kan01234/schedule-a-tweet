package com.dotterbear.file.upload.bus.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dotterbear.file.upload.db.model.ScheduledTweet;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;

@Service
public class TwitterService {

  private static final Logger log = LoggerFactory.getLogger(TwitterService.class);

  @Autowired
  private Twitter twitter;

  public void postTweet(ScheduledTweet scheduledTweet) throws TwitterException {
    StatusUpdate statusUpdate = new StatusUpdate(scheduledTweet.getTweetText());
    twitter.updateStatus(statusUpdate);
    log.debug("statusUpdate: {}", statusUpdate);
  }
}
