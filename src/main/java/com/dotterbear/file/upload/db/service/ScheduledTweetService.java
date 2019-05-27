package com.dotterbear.file.upload.db.service;

import java.util.Date;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dotterbear.file.upload.db.model.ScheduledTweet;
import com.dotterbear.file.upload.db.repo.ScheduledTweetRepository;

@Service
public class ScheduledTweetService {

  private static final Logger log = LoggerFactory.getLogger(ScheduledTweetService.class);

  @Autowired
  private ScheduledTweetRepository scheduledTweetRepository;

  public ScheduledTweet findById(String id) {
    log.debug("findById, id: {}", id);
    Optional<ScheduledTweet> scheduledTweet = scheduledTweetRepository.findById(id);
    return scheduledTweet.orElse(null);
  }

  public ScheduledTweet save(ScheduledTweet scheduledTweet) {
    log.debug("save, uploadFile: {}", scheduledTweet);
    Date now = new Date();
    if (scheduledTweet.getId() == null)
      scheduledTweet.setCreateTime(now);
    scheduledTweet.setAmendTIme(now);
    scheduledTweetRepository.save(scheduledTweet);
    return scheduledTweet;
  }

}
