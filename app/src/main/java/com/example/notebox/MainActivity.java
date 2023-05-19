package com.example.notebox;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.notebox.adapters.RecycleViewAdapter;
import com.example.notebox.models.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    getWindow()
        .setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    setContentView(R.layout.activity_main);

    RecyclerView recyclerView = findViewById(R.id.recycler_view);
    ImageView emptyNoteIcon = findViewById(R.id.imageView3);
    FloatingActionButton button = findViewById(R.id.floatingActionButton);

    List<Note> noteList = new ArrayList<>();
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//      noteList.add(
//          new Note(
//              1L,
//              "Bao cao bai tap Android",
//              "bla bla",
//              LocalDateTime.now(),
//              LocalDateTime.now(),
//              LocalDateTime.now()));
//      noteList.add(
//          new Note(
//              2L,
//              "Bao cao bai tap Android",
//              "bla bla",
//              LocalDateTime.now(),
//              LocalDateTime.now(),
//              LocalDateTime.now()));
//      noteList.add(
//          new Note(
//              3L,
//              "Bao cao bai tap Android",
//              "bla bla",
//              LocalDateTime.now(),
//              LocalDateTime.now(),
//              LocalDateTime.now()));
//      noteList.add(
//          new Note(
//              4L,
//              "Bao cao bai tap Android",
//              "bla bla",
//              LocalDateTime.now(),
//              LocalDateTime.now(),
//              LocalDateTime.now()));
//      noteList.add(
//          new Note(
//              5L,
//              "Bao cao bai tap Android",
//              "bla bla",
//              LocalDateTime.now(),
//              LocalDateTime.now(),
//              LocalDateTime.now()));
//      noteList.add(
//          new Note(
//              5L,
//              "Bao cao bai tap Android",
//              "bla bla",
//              LocalDateTime.now(),
//              LocalDateTime.now(),
//              LocalDateTime.now()));
//      noteList.add(
//          new Note(
//              5L,
//              "Bao cao bai tap Android",
//              "bla bla",
//              LocalDateTime.now(),
//              LocalDateTime.now(),
//              LocalDateTime.now()));
//    }

    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setNestedScrollingEnabled(false);
    if (!noteList.isEmpty()) {
      emptyNoteIcon.setVisibility(View.INVISIBLE);
    } else {
      emptyNoteIcon.setVisibility(View.VISIBLE);
    }
    recyclerView.setAdapter(new RecycleViewAdapter(getApplicationContext(), noteList));

    button.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
            startActivity(intent);
          }
        });
  }
}
