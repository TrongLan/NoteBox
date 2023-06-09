package com.example.notebox.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.notebox.R;
import com.example.notebox.models.Note;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class RecycleViewAdapter extends RecyclerView.Adapter<NoteItemViewHolder> {

  Context context;
  List<Note> noteList;

  NoteItemClickable noteItemClickable;

  public RecycleViewAdapter(
      Context applicationContext, List<Note> noteList, NoteItemClickable noteItemClickable) {
    this.context = applicationContext;
    this.noteList = noteList;
    this.noteItemClickable = noteItemClickable;
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
      holder.noteUpdatedAt.setText(noteList.get(position).getUpdatedDateTime());
    }
    holder.linearLayout.setOnClickListener(v -> noteItemClickable.onClick(noteList.get(position)));
  }

  @Override
  public int getItemCount() {
    return noteList.size();
  }
}
