package com.example.notebox.adapters;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.notebox.R;
import org.jetbrains.annotations.NotNull;

public class NoteItemViewHolder extends RecyclerView.ViewHolder {

  TextView noteTitle;
  TextView noteCreateAt;

  public NoteItemViewHolder(@NonNull @NotNull View itemView) {
    super(itemView);
    noteTitle = itemView.findViewById(R.id.title);
    noteCreateAt = itemView.findViewById(R.id.create_at);
  }
}
