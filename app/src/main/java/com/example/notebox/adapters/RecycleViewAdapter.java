package com.example.notebox.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.notebox.R;
import com.example.notebox.models.Note;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class RecycleViewAdapter extends RecyclerView.Adapter<NoteItemViewHolder> {

  Context context;
  List<Note> noteList;

  public RecycleViewAdapter(Context applicationContext, List<Note> noteList) {
    this.context = applicationContext;
    this.noteList = noteList;
  }

  @NonNull
  @NotNull
  @Override
  public NoteItemViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
    return new NoteItemViewHolder(
        LayoutInflater.from(context).inflate(R.layout.note_item_view, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull @NotNull NoteItemViewHolder holder, int position) {
    holder.noteTitle.setText(noteList.get(position).getTitle());
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
      holder.noteCreateAt.setText(noteList.get(position).getCreatedDateTime().format(formatter));
    }
  }

  @Override
  public int getItemCount() {
    return noteList.size();
  }
}
