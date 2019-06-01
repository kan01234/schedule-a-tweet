package com.dotterbear.file.upload.db.model;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ScheduledTweet {

  public final static String INIT = "I";

  public final static String POSTING = "P";

  public final static String DONE = "D";

  @Id
  private String id;

  private String uploadFileId;

  private String tweetText;

  private Date scheduledTime;

  private String tweetStatus = INIT;

  private long twitterStatusId;

  private long twitterUserId;

  private Date createdAt;

  private List<String> tags = Collections.emptyList();

  private Date createTime;

  private Date amendTIme;

  public ScheduledTweet() {
  }

  public ScheduledTweet(String tweetText, Date scheduledTime) {
    super();
    this.tweetText = tweetText;
    this.scheduledTime = scheduledTime;
  }

  public ScheduledTweet(String uploadFileId, String tweetText, Date scheduledTime) {
    super();
    this.uploadFileId = uploadFileId;
    this.tweetText = tweetText;
    this.scheduledTime = scheduledTime;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUploadFileId() {
    return uploadFileId;
  }

  public void setUploadFileId(String uploadFileId) {
    this.uploadFileId = uploadFileId;
  }

  public Date getScheduledTime() {
    return scheduledTime;
  }

  public void setScheduledTime(Date scheduledTime) {
    this.scheduledTime = scheduledTime;
  }

  public String getTweetStatus() {
    return tweetStatus;
  }

  public void setTweetStatus(String tweetStatus) {
    this.tweetStatus = tweetStatus;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getAmendTIme() {
    return amendTIme;
  }

  public void setAmendTIme(Date amendTIme) {
    this.amendTIme = amendTIme;
  }

  public String getTweetText() {
    return tweetText;
  }

  public void setTweetText(String tweetText) {
    this.tweetText = tweetText;
  }

  public long getTwitterStatusId() {
    return twitterStatusId;
  }

  public void setTwitterStatusId(long twitterStatusId) {
    this.twitterStatusId = twitterStatusId;
  }

  public long getTwitterUserId() {
    return twitterUserId;
  }

  public void setTwitterUserId(long twitterUserId) {
    this.twitterUserId = twitterUserId;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public List<String> getTags() {
    return tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  @Override
  public String toString() {
    return "ScheduledTweet [id=" + id + ", uploadFileId=" + uploadFileId + ", tweetText="
        + tweetText + ", scheduledTime=" + scheduledTime + ", tweetStatus=" + tweetStatus
        + ", twitterStatusId=" + twitterStatusId + ", twitterUserId=" + twitterUserId
        + ", createdAt=" + createdAt + ", tags=" + tags + ", createTime=" + createTime
        + ", amendTIme=" + amendTIme + "]";
  }

}
