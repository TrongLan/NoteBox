package com.example.notebox.models;

import android.os.Build;
import androidx.annotation.RequiresApi;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Note implements Serializable, Comparable<Note> {
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

  @RequiresApi(api = Build.VERSION_CODES.O)
  @Override
  public int compareTo(Note o) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    LocalDateTime updateTimeOfThis = LocalDateTime.parse(this.getUpdatedDateTime(), formatter);
    LocalDateTime updateTimeOfO = LocalDateTime.parse(o.getUpdatedDateTime(), formatter);
    if (updateTimeOfThis.isEqual(updateTimeOfO)) {
      return 0;
    } else {
      if (updateTimeOfThis.isAfter(updateTimeOfO)) {
        return -1;
      } else {
        return 1;
      }
    }
  }
}
