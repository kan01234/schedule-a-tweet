package com.dotterbear.file.upload.db.model;

import java.util.Date;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class UploadFile {

  @Id
  private String id;

  private String fileName;

  private List<String> tags;

  private Date createTime;

  private Date amendTIme;

  public UploadFile(String fileName) {
    this.fileName = fileName;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
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

  public List<String> getTags() {
    return tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  @Override
  public String toString() {
    return "UploadFile [id=" + id + ", fileName=" + fileName + ", tags=" + tags + ", createTime="
        + createTime + ", amendTIme=" + amendTIme + "]";
  }

}
