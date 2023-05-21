package com.example.notebox.models;

import java.io.Serializable;

public class Note implements Serializable {
  private Long id;
  private String title;
  private String content;
  private String createdDateTime;
  private String updatedDateTime;
  private String remindingDateTime;

  public Note() {}

  public Note(
      Long id,
      String title,
      String content,
      String createdDateTime,
      String updatedDateTime,
      String remindingDateTime) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.createdDateTime = createdDateTime;
    this.updatedDateTime = updatedDateTime;
    this.remindingDateTime = remindingDateTime;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getCreatedDateTime() {
    return createdDateTime;
  }

  public void setCreatedDateTime(String createdDateTime) {
    this.createdDateTime = createdDateTime;
  }

  public String getUpdatedDateTime() {
    return updatedDateTime;
  }

  public void setUpdatedDateTime(String updatedDateTime) {
    this.updatedDateTime = updatedDateTime;
  }

  public String getRemindingDateTime() {
    return remindingDateTime;
  }

  public void setRemindingDateTime(String remindingDateTime) {
    this.remindingDateTime = remindingDateTime;
  }
}
