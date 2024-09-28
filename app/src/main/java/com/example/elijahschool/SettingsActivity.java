package com.example.elijahschool;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class SettingsActivity extends AppCompatActivity {
    private LinearLayout checkboxContainer;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        checkboxContainer = findViewById(R.id.checkbox_container);
        saveButton = findViewById(R.id.save_button);

        loadPreferences();

        saveButton.setOnClickListener(v -> {
            savePreferences();
            Toast.makeText(SettingsActivity.this, "Pasirinkimai išsaugoti", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void loadOptions(String category) {
        SharedPreferences sharedPreferences = getSharedPreferences("PARENT_SETTINGS", MODE_PRIVATE);
        Set<String> selectedOptions = sharedPreferences.getStringSet(category, new HashSet<>());
        for (int i = 0; i < checkboxContainer.getChildCount(); i++) {
            if (checkboxContainer.getChildAt(i) instanceof CheckBox) {
                CheckBox checkbox = (CheckBox) checkboxContainer.getChildAt(i);
                String tag = (String) checkbox.getTag();
                if (selectedOptions.contains(tag)) {
                    checkbox.setChecked(true);
                }
            }
        }
    }

    private void saveOptions(String category) {
        SharedPreferences sharedPreferences = getSharedPreferences("PARENT_SETTINGS", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Set<String> selectedOptions = new HashSet<>();
        for (int i = 0; i < checkboxContainer.getChildCount(); i++) {
            if (checkboxContainer.getChildAt(i) instanceof CheckBox) {
                CheckBox checkbox = (CheckBox) checkboxContainer.getChildAt(i);
                String tag = (String) checkbox.getTag();
                if (checkbox.isChecked() && tag.contains(category.toLowerCase(new Locale("lt", "LT")))) {
                    selectedOptions.add(tag);
                }
            }
        }
        editor.putStringSet(category, selectedOptions);
        editor.apply();
    }

    private void loadPreferences() {
        loadOptions("Vaziuoti");
        loadOptions("Eiti");
        loadOptions("Valgyti");
        loadOptions("Gerti");
        loadOptions("Noriu");
        loadOptions("Skauda");
        loadOptions("As");
    }

    private void savePreferences() {
        saveOptions("Vaziuoti");
        saveOptions("Eiti");
        saveOptions("Valgyti");
        saveOptions("Gerti");
        saveOptions("Noriu");
        saveOptions("Skauda");
        saveOptions("As");

        Toast.makeText(this, "Pasirinkimai išsaugoti", Toast.LENGTH_SHORT).show();
    }
}
