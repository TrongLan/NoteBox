package com.example.notebox;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.notebox.models.Note;
import com.example.notebox.sql.NoteSQLiteHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NoteDetailActivity extends AppCompatActivity {

  private TextView createdTime;
  private TextView updatedTime;
  private TextView remindTime;
  private TextView noteTitle;
  private TextView noteContent;

  private FloatingActionButton menuButton;
  private FloatingActionButton editButton;
  private FloatingActionButton deleteButton;
  private Float transitionYAxis = 100F;
  private boolean menuOpen = false;
  private NoteSQLiteHelper noteSQLiteHelper;
  private long needUpdateNoteId;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // full màn hình
    getWindow()
        .setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.note_detail_ui);

    this.showExpandedFloatingButtonMenu();
    this.displayNoteData();
  }

  private void showExpandedFloatingButtonMenu() {
    menuButton = findViewById(R.id.menu_button);
    editButton = findViewById(R.id.edit_button);
    deleteButton = findViewById(R.id.delete_button);

    editButton.setAlpha(0f);
    deleteButton.setAlpha(0f);
    menuButton.setAlpha(0.5f);

    editButton.setTranslationY(transitionYAxis);
    deleteButton.setTranslationY(transitionYAxis);

    menuButton.setOnClickListener(
        v -> {
          if (menuOpen) {
            hideFloatingMenu();
          } else {
            openFloatingMenu();
          }
        });

    editButton.setOnClickListener(
        v -> {
          Intent intent = new Intent(NoteDetailActivity.this, UpdateNoteActivity.class);
          intent.putExtra("needUpdateId", this.needUpdateNoteId);
          startActivity(intent);
        });

    deleteButton.setOnClickListener(
        v -> {
          AlertDialog.Builder builder = new AlertDialog.Builder(NoteDetailActivity.this);
          builder.setTitle("Xác nhận xóa");
          builder.setMessage("Bạn có chắc muốn xóa bản ghi chú này?");
          builder.setCancelable(false);
          builder.setPositiveButton(
              "Đồng ý",
              (dialogInterface, i) -> {
                Intent intent = getIntent();
                long newId = intent.getLongExtra("newId", 0);
                noteSQLiteHelper.deleteById(newId);
                Toast toast =
                    Toast.makeText(
                        NoteDetailActivity.this, "Xóa ghi chú thành công", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                Intent intent1 = new Intent(NoteDetailActivity.this, MainActivity.class);
                startActivity(intent1);
              });
          builder.setNegativeButton("Hủy bỏ", (dialogInterface, i) -> dialogInterface.dismiss());

          AlertDialog alertDialog = builder.create();
          alertDialog.setOnShowListener(
              arg0 -> {
                alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.section_in_main);
                alertDialog
                    .getButton(AlertDialog.BUTTON_NEGATIVE)
                    .setTextColor(Color.rgb(0, 106, 147));
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.GRAY);
              });
          alertDialog.show();
        });
  }

  private void hideFloatingMenu() {
    menuOpen = !menuOpen;
    menuButton.animate().rotation(0).setDuration(300).withLayer().start();
    menuButton.setAlpha(0.5f);
    editButton.animate().translationY(transitionYAxis).alpha(0f).setDuration(300).start();
    deleteButton.animate().translationY(transitionYAxis).alpha(0f).setDuration(300).start();
  }

  private void openFloatingMenu() {
    menuOpen = !menuOpen;
    menuButton.animate().rotation(180).setDuration(300).withLayer().start();
    menuButton.setAlpha(1f);
    editButton.animate().translationY(0f).alpha(1f).setDuration(300).start();
    deleteButton.animate().translationY(0f).alpha(1f).setDuration(300).start();
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
    this.needUpdateNoteId = newId;
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
