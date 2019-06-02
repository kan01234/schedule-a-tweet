package com.dotterbear.schedule.a.tweet.db.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.dotterbear.schedule.a.tweet.db.model.ScheduledTweet;

@Repository
public interface ScheduledTweetRepository extends MongoRepository<ScheduledTweet, String> {
}
