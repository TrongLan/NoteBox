package com.example.notebox;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.notebox.adapters.NoteItemClickable;
import com.example.notebox.adapters.RecycleViewAdapter;
import com.example.notebox.models.Note;
import com.example.notebox.sql.NoteSQLiteHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NoteItemClickable {
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
    Collections.sort(noteList);

    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setNestedScrollingEnabled(false);
    if (!noteList.isEmpty()) {
      emptyNoteIcon.setVisibility(View.INVISIBLE);
    } else {
      emptyNoteIcon.setVisibility(View.VISIBLE);
    }
    RecycleViewAdapter adapter = new RecycleViewAdapter(getApplicationContext(), noteList, this);
    recyclerView.setAdapter(adapter);

    ItemTouchHelper itemTouchHelper =
        new ItemTouchHelper(this.swipeEventProcess(adapter, noteList));
    itemTouchHelper.attachToRecyclerView(recyclerView);

    fabClickEventProcess();
  }

  @Override
  public void onBackPressed() {
    if (doubleBackToExitPressedOnce) {
      super.onBackPressed();
      finishAffinity();
    }
    this.doubleBackToExitPressedOnce = true;
    Toast toast = Toast.makeText(this, "Nhấn back lần nữa để thoát", Toast.LENGTH_SHORT);
    toast.setGravity(Gravity.CENTER, 0, 0);
    toast.show();

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

  // Xử lý sự kiện gạt để xóa ghi chú
  private ItemTouchHelper.SimpleCallback swipeEventProcess(
      RecycleViewAdapter adapter, List<Note> noteList) {
    return new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

      @Override
      public boolean onMove(
          RecyclerView recyclerView,
          RecyclerView.ViewHolder viewHolder,
          RecyclerView.ViewHolder target) {
        Toast.makeText(MainActivity.this, "on Move", Toast.LENGTH_SHORT).show();
        return false;
      }

      @Override
      public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
        // Gạt để mở popup xác nhận xóa
        int position = viewHolder.getAdapterPosition();
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có chắc muốn xóa bản ghi chú này?");
        builder.setCancelable(false);
        builder.setPositiveButton(
            "Đồng ý",
            (dialogInterface, i) -> {
              Note n = noteList.remove(position);
              noteSQLiteHelper.deleteById(n.getId());
              Toast toast =
                  Toast.makeText(MainActivity.this, "Xóa ghi chú thành công", Toast.LENGTH_SHORT);
              toast.setGravity(Gravity.CENTER, 0, 0);
              toast.show();
              adapter.notifyDataSetChanged();
              dialogInterface.dismiss();
            });
        builder.setNegativeButton(
            "Hủy bỏ",
            (dialogInterface, i) -> {
              adapter.notifyItemChanged(viewHolder.getAdapterPosition());
              dialogInterface.dismiss();
            });

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
      }
    };
  }

  @Override
  public void onClick(Note n) {
    Intent intent = new Intent(MainActivity.this, NoteDetailActivity.class);
    intent.putExtra("newId", n.getId());
    startActivity(intent);
  }
}
