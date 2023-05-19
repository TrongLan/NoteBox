package com.example.notebox;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class AddNoteActivity extends AppCompatActivity {

  private ImageButton setDateButton;
  private Button noteSaveButton;
  private EditText remindTimeInput;

  @RequiresApi(api = Build.VERSION_CODES.O)
  @Override
  protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    getWindow()
        .setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    setContentView(R.layout.add_note_ui);

    setDateButton = findViewById(R.id.imageButton);
    noteSaveButton = findViewById(R.id.button);
    remindTimeInput = findViewById(R.id.editTextTime);

    setDateButton.setOnClickListener(v -> openDateDialog(LocalDateTime.now()));
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
            new TimePickerDialog.OnTimeSetListener() {
              @Override
              public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                LocalTime localTime = LocalTime.of(hourOfDay, minute, 0);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                remindTimeInput.setText(String.format("%s %s", date, formatter.format(localTime)));
              }
            },
            dateTime.getHour(),
            dateTime.getMinute(),
            true);
    timePickerDialog.show();
  }
}
