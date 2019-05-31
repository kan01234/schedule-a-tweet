package com.dotterbear.file.upload.db.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import com.dotterbear.file.upload.db.model.ScheduledTweet;
import com.dotterbear.file.upload.db.repo.ScheduledTweetRepository;

@Service
public class ScheduledTweetService {

  private static final Logger log = LoggerFactory.getLogger(ScheduledTweetService.class);

  @Autowired
  private ScheduledTweetRepository scheduledTweetRepository;

  @Autowired
  private MongoTemplate mongoTemplate;

  public ScheduledTweet findById(String id) {
    log.debug("findById, id: {}", id);
    Optional<ScheduledTweet> scheduledTweet = scheduledTweetRepository.findById(id);
    return scheduledTweet.orElse(null);
  }

  public List<ScheduledTweet> findAllByTweetStatusAndscheduledTimeLessThanOrEquals(String status,
      Date date) {
    log.debug("findAllByTweetStatusAndscheduledTimeLessThanOrEquals, status: {}, date: {}", status,
        date);
    Query query = new Query(Criteria.where("tweetStatus").is(status).and("scheduledTime").lte(date));
    return mongoTemplate.find(query, ScheduledTweet.class);
  }

  public List<ScheduledTweet> findAll(Query query) {
    log.debug("findAllByTweetStaus, query: {}", query);
    return mongoTemplate.find(query, ScheduledTweet.class);
  }

  public ScheduledTweet save(ScheduledTweet scheduledTweet) {
    log.debug("save, scheduledTweet: {}", scheduledTweet);
    Date now = new Date();
    if (scheduledTweet.getId() == null)
      scheduledTweet.setCreateTime(now);
    scheduledTweet.setAmendTIme(now);
    scheduledTweetRepository.save(scheduledTweet);
    return scheduledTweet;
  }

}
