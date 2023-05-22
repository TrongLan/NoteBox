package com.example.notebox.sql;

import android.content.ContentValues;
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
      note.setId(cursor.getLong(0));
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
  public long addNote(Note newNote) {
    ContentValues contentValues = new ContentValues();
    contentValues.put("TITLE", newNote.getTitle());
    contentValues.put("CONTENT", newNote.getContent());
    contentValues.put("CREATE_DATETIME", newNote.getCreatedDateTime());
    contentValues.put("UPDATE_DATETIME", newNote.getUpdatedDateTime());
    contentValues.put("REMIND_DATETIME", newNote.getRemindingDateTime());

    SQLiteDatabase sqLiteDatabase = getWritableDatabase();
    return sqLiteDatabase.insert("NOTE", null, contentValues);
  }

  // Tìm bản ghi theo id
  public Note getById(long id) {
    Note note = new Note();
    SQLiteDatabase sqLiteDatabase = getReadableDatabase();
    String selection = "id = ?";
    String[] args = {String.valueOf(id)};
    Cursor cursor = sqLiteDatabase.query("NOTE", null, selection, args, null, null, null);
    while (cursor.moveToNext()) {
      note.setId(cursor.getLong(0));
      note.setTitle(cursor.getString(1));
      note.setContent(cursor.getString(2));
      note.setCreatedDateTime(cursor.getString(3));
      note.setUpdatedDateTime(cursor.getString(4));
      note.setRemindingDateTime(cursor.getString(5));
    }
    return note;
  }

  // Xóa ghi chú theo id
  public void deleteById(long id) {
    SQLiteDatabase sqLiteDatabase = getWritableDatabase();
    sqLiteDatabase.delete("NOTE", "id=?", new String[] {String.valueOf(id)});
  }
}
