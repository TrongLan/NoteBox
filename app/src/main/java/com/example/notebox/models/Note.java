package com.example.notebox.models;

import java.time.LocalDateTime;

public class Note {
  private Long id;
  private String title;
  private String content;
  private LocalDateTime createdDateTime;
  private LocalDateTime updatedDateTime;
  private LocalDateTime remindingDateTime;

  public Note() {}

  public Note(
      Long id,
      String title,
      String content,
      LocalDateTime createdDateTime,
      LocalDateTime updatedDateTime,
      LocalDateTime remindingDateTime) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.createdDateTime = createdDateTime;
    this.updatedDateTime = updatedDateTime;
    this.remindingDateTime = remindingDateTime;
  }

  public LocalDateTime getUpdatedDateTime() {
    return updatedDateTime;
  }

  public void setUpdatedDateTime(LocalDateTime updatedDateTime) {
    this.updatedDateTime = updatedDateTime;
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

  public LocalDateTime getCreatedDateTime() {
    return createdDateTime;
  }

  public void setCreatedDateTime(LocalDateTime createdDateTime) {
    this.createdDateTime = createdDateTime;
  }

  public LocalDateTime getRemindingDateTime() {
    return remindingDateTime;
  }

  public void setRemindingDateTime(LocalDateTime remindingDateTime) {
    this.remindingDateTime = remindingDateTime;
  }
}
