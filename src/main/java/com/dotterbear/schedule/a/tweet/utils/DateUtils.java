package com.dotterbear.schedule.a.tweet.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DateUtils {

  private SimpleDateFormat tweetRequestSdf;

  public DateUtils(@Value("${com.dotterbear.format.request.tweet-date}") String tweetRequestDateFormat) {
    tweetRequestSdf = new SimpleDateFormat(tweetRequestDateFormat);
  }

  public Date parseTweetRequestDate(String str) throws ParseException {
    if (str == null || str.isEmpty())
      return null;
    return tweetRequestSdf.parse(str);
  }
}
