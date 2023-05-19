package com.example.notebox;

import android.content.Intent;
import android.os.*;
import androidx.appcompat.app.AppCompatActivity;

public class NoteBoxSplashScreen extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    HandlerThread handlerThread = new HandlerThread("HandlerThread");
    handlerThread.start();
    Handler handler = new Handler(handlerThread.getLooper());
    handler.postDelayed(
        () -> {
          startActivity(new Intent(NoteBoxSplashScreen.this, MainActivity.class));
          finish();
        },
        2000);
  }
}
