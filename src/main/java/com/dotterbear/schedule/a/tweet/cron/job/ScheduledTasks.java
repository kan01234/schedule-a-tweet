package com.dotterbear.schedule.a.tweet.cron.job;

import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.dotterbear.schedule.a.tweet.bus.service.TwitterService;
import com.dotterbear.schedule.a.tweet.db.model.ScheduledTweet;
import com.dotterbear.schedule.a.tweet.db.service.ScheduledTweetService;
import twitter4j.Status;

@Component
public class ScheduledTasks {

  private Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

  @Autowired
  private TwitterService twitterService;

  @Autowired
  private ScheduledTweetService scheduledTweetService;

  @Scheduled(cron = "0/10 * * * * * ")
  public void postScheduledTweet() {
    log.debug("postScheduledTweet");
    List<ScheduledTweet> scheduledTweets = scheduledTweetService
        .findAllByTweetStatusAndscheduledTimeLessThanOrEquals(ScheduledTweet.INIT, new Date());
    scheduledTweets.stream().forEach(st -> {
      st.setTweetStatus(ScheduledTweet.POSTING);
      scheduledTweetService.save(st);
    });
    scheduledTweets.stream().forEach(st -> {
      try {
        Status status = twitterService.postTweet(st);
        st.setTweetStatus(ScheduledTweet.DONE);
      } catch (Exception e) {
        log.error("fail to post tweet", e);
        st.setTweetStatus(ScheduledTweet.INIT);
      } finally {
        scheduledTweetService.save(st);
      }
    });
  }
}
