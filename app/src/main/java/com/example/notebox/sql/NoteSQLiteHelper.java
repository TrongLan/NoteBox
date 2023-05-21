package com.example.notebox.sql;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import com.example.notebox.models.Note;
import java.util.ArrayList;
import java.util.List;

public class NoteSQLiteHelper extends SQLiteOpenHelper {

  private static final String DATABASE_NAME = "notebox.db";
  private static final int DATABASE_VERSION = 1;

  public NoteSQLiteHelper(@Nullable Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(SQLCommand.CREATE_TABLE_FOR_NOTE.getCommand());
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

  // Lấy danh sách ghi chú
  public List<Note> getAllNotes() {
    List<Note> noteList = new ArrayList<>();
    SQLiteDatabase sqLiteDatabase = getReadableDatabase();
    Cursor cursor = sqLiteDatabase.query("NOTE", null, null, null, null, null, null);
    while (cursor.moveToNext()) {
      Note note = new Note();
      note.setTitle(cursor.getString(1));
      note.setContent(cursor.getString(2));
      note.setCreatedDateTime(cursor.getString(3));
      note.setUpdatedDateTime(cursor.getString(4));
      note.setRemindingDateTime(cursor.getString(5));
      noteList.add(note);
    }
    return noteList;
  }

  // Thêm ghi chú
  public void addNote(Note newNote) {
    SQLiteDatabase sqLiteDatabase = getWritableDatabase();
    Object[] args = {
      newNote.getTitle(),
      newNote.getContent(),
      newNote.getCreatedDateTime(),
      newNote.getUpdatedDateTime(),
      newNote.getRemindingDateTime()
    };
    sqLiteDatabase.execSQL(SQLCommand.INSERT_TABLE_NOTE.getCommand(), args);
  }
}
