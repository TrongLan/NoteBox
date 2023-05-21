package com.example.notebox;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.notebox.adapters.RecycleViewAdapter;
import com.example.notebox.models.Note;
import com.example.notebox.sql.NoteSQLiteHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class MainActivity extends AppCompatActivity {
  private RecyclerView recyclerView;
  private ImageView emptyNoteIcon;
  private FloatingActionButton button;

  private NoteSQLiteHelper noteSQLiteHelper;
  private boolean doubleBackToExitPressedOnce = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    getWindow()
        .setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    setContentView(R.layout.activity_main);

    recyclerView = findViewById(R.id.recycler_view);
    emptyNoteIcon = findViewById(R.id.imageView3);

    noteSQLiteHelper = new NoteSQLiteHelper(getApplicationContext());
    List<Note> noteList = noteSQLiteHelper.getAllNotes();

    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setNestedScrollingEnabled(false);
    if (!noteList.isEmpty()) {
      emptyNoteIcon.setVisibility(View.INVISIBLE);
    } else {
      emptyNoteIcon.setVisibility(View.VISIBLE);
    }
    recyclerView.setAdapter(new RecycleViewAdapter(getApplicationContext(), noteList));

    fabClickEventProcess();
  }

  @Override
  public void onBackPressed() {
    if (doubleBackToExitPressedOnce) {
      super.onBackPressed();
      finishAffinity();
    }
    this.doubleBackToExitPressedOnce = true;
    Toast.makeText(this, "Nhấn back lần nữa để thoát", Toast.LENGTH_SHORT).show();

    new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
  }

  // Xử lý sự kiện ấn nút thêm ghi chú
  private void fabClickEventProcess() {
    button = findViewById(R.id.floatingActionButton);
    button.setOnClickListener(
        v -> {
          Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
          startActivity(intent);
        });
  }
}
