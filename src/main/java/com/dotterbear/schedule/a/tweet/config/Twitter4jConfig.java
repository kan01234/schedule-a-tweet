package com.dotterbear.schedule.a.tweet.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

@Configuration
public class Twitter4jConfig {

  @Bean
  public Twitter getTwitter(@Value("${com.dotterbear.twitter.consumer.key}") String consumerKey,
      @Value("${com.dotterbear.twitter.consumer.secret}") String consumerSecret,
      @Value("${com.dotterbear.twitter.access.token}") String accessToken,
      @Value("${com.dotterbear.twitter.access.secret}") String accessSecret) {
    ConfigurationBuilder cb = new ConfigurationBuilder();
    cb.setDebugEnabled(true).setOAuthConsumerKey(consumerKey)
        .setOAuthConsumerSecret(consumerSecret)
        .setOAuthAccessToken(accessToken)
        .setOAuthAccessTokenSecret(accessSecret);
    TwitterFactory tf = new TwitterFactory(cb.build());
    return tf.getInstance();
  }

}
