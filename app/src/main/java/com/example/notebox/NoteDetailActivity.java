package com.example.notebox;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.notebox.models.Note;
import com.example.notebox.sql.NoteSQLiteHelper;

public class NoteDetailActivity extends AppCompatActivity {

  private TextView createdTime;
  private TextView updatedTime;
  private TextView remindTime;
  private TextView noteTitle;
  private TextView noteContent;
  private NoteSQLiteHelper noteSQLiteHelper;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // full màn hình
    getWindow()
        .setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.note_detail_ui);

    this.displayNoteData();
  }

  @Override
  public void onBackPressed() {
    Intent intent = new Intent(NoteDetailActivity.this, MainActivity.class);
    startActivity(intent);
    finish();
  }

  private void displayNoteData() {
    Intent intent = getIntent();
    long newId = intent.getLongExtra("newId", 0);
    noteSQLiteHelper = new NoteSQLiteHelper(getApplicationContext());
    Note note = noteSQLiteHelper.getById(newId);

    createdTime = findViewById(R.id.created_time);
    updatedTime = findViewById(R.id.last_update_time);
    remindTime = findViewById(R.id.remind_time);
    noteTitle = findViewById(R.id.title_textview);
    noteContent = findViewById(R.id.content_textview);

    createdTime.setText(note.getCreatedDateTime());
    updatedTime.setText(note.getUpdatedDateTime());
    remindTime.setText(note.getRemindingDateTime());
    noteTitle.setText(note.getTitle());
    noteContent.setText(note.getContent());
    noteContent.setMovementMethod(new ScrollingMovementMethod());
  }
}
