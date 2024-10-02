package com.example.elijahschool;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class GameRoomsActivity extends AppCompatActivity {

    private TextToSpeech tts;
    private boolean isItemZoomed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_rooms);

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    tts.setLanguage(new Locale("lt", "LT"));  // Lithuanian TTS
                }
            }
        });

        ImageView fridge = findViewById(R.id.item_fridge);
        ImageView table = findViewById(R.id.item_table);

        fridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isItemZoomed) {
                    resetItemPosition(fridge);  // Zoom out
                } else {
                    zoomInItem(fridge, "Šaldytuvas");
                }
            }
        });

        table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isItemZoomed) {
                    resetItemPosition(table);  // Zoom out
                } else {
                    zoomInItem(table, "Stalas");
                }
            }
        });
    }

    private void zoomInItem(View item, String itemName) {
        item.animate().scaleX(2f).scaleY(2f).setDuration(300).start();
        tts.speak(itemName, TextToSpeech.QUEUE_FLUSH, null, null);
        isItemZoomed = true;
    }

    private void resetItemPosition(View item) {
        item.animate().scaleX(1f).scaleY(1f).setDuration(300).start();
        isItemZoomed = false;
    }

    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.shutdown();
        }
        super.onDestroy();
    }
}