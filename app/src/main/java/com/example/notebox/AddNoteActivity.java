package com.example.notebox;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.example.notebox.models.Note;
import com.example.notebox.sql.NoteSQLiteHelper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class AddNoteActivity extends AppCompatActivity {

  private ImageButton setRemindTimeButton;
  private Button noteSaveButton;
  private EditText remindTimeInput;
  private TextView titleInput;
  private TextView contentInput;

  @RequiresApi(api = Build.VERSION_CODES.O)
  @Override
  protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // Full màn hình
    getWindow()
        .setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.add_note_ui);

    setRemindTimeButton = findViewById(R.id.imageButton);
    setRemindTimeButton.setOnClickListener(v -> openDateDialog(LocalDateTime.now()));

    this.saveNoteEventProcess(
        new NoteSQLiteHelper(getApplicationContext()));
  }

  @RequiresApi(api = Build.VERSION_CODES.O)
  private void openDateDialog(LocalDateTime dateTime) {
    DatePickerDialog datePickerDialog =
        new DatePickerDialog(
            this,
            R.style.dialogTheme,
            (view, year, month, dayOfMonth) -> {
              LocalDate date = LocalDate.of(year, month + 1, dayOfMonth);
              DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
              openTimeDialog(dateTime, date.format(formatter));
            },
            dateTime.getYear(),
            dateTime.getMonthValue() - 1,
            dateTime.getDayOfMonth());
    datePickerDialog
        .getDatePicker()
        .setMinDate(
            dateTime.toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
    datePickerDialog.show();
  }

  @RequiresApi(api = Build.VERSION_CODES.O)
  private void openTimeDialog(LocalDateTime dateTime, String date) {
    TimePickerDialog timePickerDialog =
        new TimePickerDialog(
            this,
            R.style.dialogTheme,
            (view, hourOfDay, minute) -> {
              LocalTime localTime = LocalTime.of(hourOfDay, minute, 0);
              DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
              remindTimeInput.setText(String.format("%s %s", date, formatter.format(localTime)));
            },
            dateTime.getHour(),
            dateTime.getMinute(),
            true);
    timePickerDialog.show();
  }

  @RequiresApi(api = Build.VERSION_CODES.O)
  private Note mapToNoteObject() {
    titleInput = findViewById(R.id.title_input);
    contentInput = findViewById(R.id.content_input);
    remindTimeInput = findViewById(R.id.remind_time_input);
    Note note = new Note();
    note.setTitle(titleInput.getText().toString());
    note.setContent(contentInput.getText().toString());
    note.setRemindingDateTime(remindTimeInput.getText().toString());
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm");
    LocalDateTime now = LocalDateTime.now();
    note.setCreatedDateTime(now.format(formatter));
    note.setUpdatedDateTime(now.format(formatter));
    return note;
  }

  @RequiresApi(api = Build.VERSION_CODES.O)
  private void saveNoteEventProcess(NoteSQLiteHelper sqLiteHelper) {
    noteSaveButton = findViewById(R.id.note_save_button);
    noteSaveButton.setOnClickListener(
        v -> {
          sqLiteHelper.addNote(this.mapToNoteObject());
          Intent intent = new Intent(AddNoteActivity.this, MainActivity.class);
          startActivity(intent);
        });
  }
}
