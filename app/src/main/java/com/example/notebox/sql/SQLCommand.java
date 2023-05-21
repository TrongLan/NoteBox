package com.example.notebox.sql;

public enum SQLCommand {
  CREATE_TABLE_FOR_NOTE(
      "CREATE TABLE IF NOT EXISTS NOTE(ID INTEGER PRIMARY KEY AUTOINCREMENT, TITLE VARCHAR(30), CONTENT TEXT, CREATE_DATETIME TEXT, UPDATE_DATETIME TEXT, REMIND_DATETIME TEXT)"),
  INSERT_TABLE_NOTE("INSERT INTO NOTE VALUES (null,?,?,?,?,?)");

  private final String command;

  SQLCommand(String command) {
    this.command = command;
  }

  public String getCommand() {
    return command;
  }
}
