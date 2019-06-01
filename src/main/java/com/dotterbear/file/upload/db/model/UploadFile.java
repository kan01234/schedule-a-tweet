package com.dotterbear.file.upload.db.model;

import java.util.Date;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.google.cloud.vision.v1.EntityAnnotation;

@Document
public class UploadFile {

  @Id
  private String id;

  private String fileName;

  private List<EntityAnnotation> labels;

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

  public List<EntityAnnotation> getLabels() {
    return labels;
  }

  public void setLabels(List<EntityAnnotation> labels) {
    this.labels = labels;
  }

  @Override
  public String toString() {
    return "UploadFile [id=" + id + ", fileName=" + fileName + ", labels=" + labels
        + ", createTime=" + createTime + ", amendTIme=" + amendTIme + "]";
  }

}
