package com.example.notebox.configuration;

public enum ValidationMessage {
  BLANK("%s không được để trống"),
  INVALID_LENGTH("%s không được dài quá %s ký tự"),
  INVALID_DATETIME("Thời gian %s không hợp lệ");

  private final String content;

  ValidationMessage(String content) {
    this.content = content;
  }

  public String getContent() {
    return content;
  }
}
