package com.example.notebox;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.*;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.example.notebox.configuration.ValidationMessage;
import com.example.notebox.models.Note;
import com.example.notebox.sql.NoteSQLiteHelper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class UpdateNoteActivity extends AppCompatActivity {

  private ImageButton setRemindTimeButton;
  private Button noteSaveButton;
  private EditText remindTimeInput;
  private TextView titleInput;
  private TextView contentInput;

  @RequiresApi(api = Build.VERSION_CODES.O)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // Full màn hình
    getWindow()
        .setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.update_note_ui);

    titleInput = findViewById(R.id.title_input_update);
    contentInput = findViewById(R.id.content_input_update);
    remindTimeInput = findViewById(R.id.remind_time_input_update);
    setRemindTimeButton = findViewById(R.id.imageButtonUpdate);
    setRemindTimeButton.setOnClickListener(v -> openDateDialog(LocalDateTime.now()));

    NoteSQLiteHelper noteSQLiteHelper = new NoteSQLiteHelper(getApplicationContext());
    Intent intent = getIntent();
    long needUpdateId = intent.getLongExtra("needUpdateId", 0);
    Note byId = noteSQLiteHelper.getById(needUpdateId);
    titleInput.setText(byId.getTitle());
    contentInput.setText(byId.getContent());
    remindTimeInput.setText(byId.getRemindingDateTime());

    this.saveNoteEventProcess(needUpdateId, noteSQLiteHelper);
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
              remindTimeInput = findViewById(R.id.remind_time_input_update);
              remindTimeInput.setText(String.format("%s %s", date, formatter.format(localTime)));
            },
            dateTime.getHour(),
            dateTime.getMinute(),
            true);
    timePickerDialog.show();
  }

  @RequiresApi(api = Build.VERSION_CODES.O)
  private void saveNoteEventProcess(long id, NoteSQLiteHelper sqLiteHelper) {
    noteSaveButton = findViewById(R.id.note_update_button);
    noteSaveButton.setOnClickListener(
        v -> {
          Note update = this.mapToNoteObject();
          if (isValid(update)) {
            sqLiteHelper.updateNote(id, update);
            Toast toast = Toast.makeText(this, "Đã lưu thay đổi thành công", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            Intent intent = new Intent(UpdateNoteActivity.this, NoteDetailActivity.class);
            intent.putExtra("newId", id);
            startActivity(intent);
          }
        });
  }

  @RequiresApi(api = Build.VERSION_CODES.O)
  private Note mapToNoteObject() {
    Note note = new Note();
    note.setTitle(titleInput.getText().toString().trim());
    note.setContent(contentInput.getText().toString().trim());
    note.setRemindingDateTime(remindTimeInput.getText().toString().trim());
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    LocalDateTime now = LocalDateTime.now();
    note.setUpdatedDateTime(now.format(formatter));
    return note;
  }

  @RequiresApi(api = Build.VERSION_CODES.O)
  private boolean isValid(Note note) {
    if (note.getTitle().isBlank()) {
      Toast toast =
          Toast.makeText(
              this,
              String.format(ValidationMessage.BLANK.getContent(), "Tiêu đề"),
              Toast.LENGTH_SHORT);
      toast.setGravity(Gravity.CENTER, 0, 0);
      toast.show();
      return false;
    }
    if (note.getTitle().length() > 30) {
      Toast toast =
          Toast.makeText(
              this,
              String.format(ValidationMessage.INVALID_LENGTH.getContent(), "Tiêu đề", 30),
              Toast.LENGTH_SHORT);
      toast.setGravity(Gravity.CENTER, 0, 0);
      toast.show();
      return false;
    }
    if (note.getTitle().isBlank()) {
      Toast toast =
          Toast.makeText(
              this,
              String.format(ValidationMessage.BLANK.getContent(), "Nội dung ghi chú"),
              Toast.LENGTH_SHORT);
      toast.setGravity(Gravity.CENTER, 0, 0);
      toast.show();
      return false;
    }
    if (!note.getRemindingDateTime().isBlank()) {
      DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
      Toast toast =
          Toast.makeText(
              this,
              String.format(ValidationMessage.INVALID_DATETIME.getContent(), "nhắc nhở"),
              Toast.LENGTH_SHORT);
      toast.setGravity(Gravity.CENTER, 0, 0);
      try {
        LocalDateTime parse = LocalDateTime.parse(note.getRemindingDateTime(), format);
        if (parse.isBefore(LocalDateTime.now())) {
          toast.setGravity(Gravity.CENTER, 0, 0);
          toast.show();
          return false;
        }
      } catch (Exception e) {
        toast.show();
        return false;
      }
    }
    return true;
  }
}
