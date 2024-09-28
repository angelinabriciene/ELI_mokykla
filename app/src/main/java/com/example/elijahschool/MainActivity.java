package com.example.elijahschool;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements CustomAdapter.OnItemClickListener {

    private TextToSpeech tts;
    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    private List<Item> itemList;
    private LinearLayout optionLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        Button backButton = findViewById(R.id.back_button);

        Button openSettingsButton = findViewById(R.id.open_settings_button);
        openSettingsButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setVisibility(View.VISIBLE);
                optionLayout.removeAllViews();
                backButton.setVisibility(View.GONE);
            }
        });

        openSettingsButton.setOnClickListener(v -> Toast.makeText(MainActivity.this, "Palieskite ir laikykite", Toast.LENGTH_SHORT).show());

        itemList = new ArrayList<>();
        itemList.add(new Item(R.drawable.vaziuoti, "Važiuoti"));
        itemList.add(new Item(R.drawable.eiti, "Eiti"));
        itemList.add(new Item(R.drawable.valgyti, "Valgyti"));
        itemList.add(new Item(R.drawable.gerti, "Gerti"));
        itemList.add(new Item(R.drawable.man, "Noriu"));
        itemList.add(new Item(R.drawable.skauda, "Skauda"));
        itemList.add(new Item(R.drawable.as, "Aš"));

        adapter = new CustomAdapter(itemList, this, this);
        recyclerView.setAdapter(adapter);

        optionLayout = findViewById(R.id.optionLayout);

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(new Locale("lt", "LT"));
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Lithuanian language is not supported!");
                    } else {
                        Log.d("TTS", "TTS is initialized successfully!");
                    }
                } else {
                    Log.e("TTS", "TTS initialization failed!");
                }
            }
        });
    }

    @Override
    public void onItemClick(Item item) {
        optionLayout.removeAllViews();
        List<Item> secondaryOptions = new ArrayList<>();
        switch (item.getText()) {
            case "Važiuoti":
                handleOption("Važiuoti");
                secondaryOptions = getSelectedOptions("Vaziuoti");
                break;
            case "Eiti":
                handleOption("Eiti");
                secondaryOptions = getSelectedOptions("Eiti");
                break;
            case "Valgyti":
                handleOption("Valgyti");
                secondaryOptions = getSelectedOptions("Valgyti");
                break;
            case "Gerti":
                handleOption("Gerti");
                secondaryOptions = getSelectedOptions("Gerti");
                break;
            case "Noriu":
                handleOption("Noriu");
                secondaryOptions = getSelectedOptions("Noriu");
                break;
            case "Skauda":
                handleOption("Skauda");
                secondaryOptions = getSelectedOptions("Skauda");
                break;
            case "Aš":
                handleOption("Aš");
                secondaryOptions = getSelectedOptions("As");
                break;
            default:
                break;
        }
        if (!secondaryOptions.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            Button backButton = findViewById(R.id.back_button);
            backButton.setVisibility(View.VISIBLE);
            showSecondaryOptions(secondaryOptions);
        } else {
            Log.d("MainActivity", "No secondary options to display.");
        }
    }

    private List<Item> getSelectedOptions(String category) {
        List<Item> selectedOptions = new ArrayList<>();
        SharedPreferences prefs = getSharedPreferences("PARENT_SETTINGS", MODE_PRIVATE);
        Set<String> selectedOptionSet = prefs.getStringSet(category, new HashSet<>());

        for (String option : selectedOptionSet) {
            switch (category) {
                case "Vaziuoti":
                    switch (option) {
                        case "vaziuoti_namo_enabled":
                            selectedOptions.add(new Item(R.drawable.namo, "Namo"));
                            break;
                        case "vaziuoti_parduotuve_enabled":
                            selectedOptions.add(new Item(R.drawable.parduotuve, "Į parduotuvę"));
                            break;
                        case "vaziuoti_parkas_enabled":
                            selectedOptions.add(new Item(R.drawable.seimosparkas, "Į parką"));
                            break;
                        case "vaziuoti_ezero_enabled":
                            selectedOptions.add(new Item(R.drawable.ezero, "Prie ežero"));
                            break;
                        case "vaziuoti_miesta_enabled":
                            selectedOptions.add(new Item(R.drawable.miestas, "Į miestą"));
                            break;
                        case "vaziuoti_fontano_enabled":
                            selectedOptions.add(new Item(R.drawable.fontanas, "Prie fontano"));
                            break;
                        case "vaziuoti_mociute_meile_enabled":
                            selectedOptions.add(new Item(R.drawable.meile, "Pas močiutę Meilę"));
                            break;
                        case "vaziuoti_mociute_vale_enabled":
                            selectedOptions.add(new Item(R.drawable.vale, "Pas močiutę Valę"));
                            break;
                    }
                    break;
                case "Eiti":
                    switch (option) {
                        case "eiti_miska_enabled":
                            selectedOptions.add(new Item(R.drawable.miskas, "Į mišką"));
                            break;
                        case "eiti_pasivaikscioti_enabled":
                            selectedOptions.add(new Item(R.drawable.pasivaikscioti, "Pasivaikščioti"));
                            break;
                        case "eiti_kiema_enabled":
                            selectedOptions.add(new Item(R.drawable.parkas, "Į kiemą"));
                            break;
                        case "eiti_gaminti_maista_enabled":
                            selectedOptions.add(new Item(R.drawable.gaminti, "Gaminti maistą"));
                            break;
                    }
                    break;
                case "Valgyti":
                    switch (option) {
                        case "valgyti_sriuba_enabled":
                            selectedOptions.add(new Item(R.drawable.sriuba, "Sriuba"));
                            break;
                        case "valgyti_mesa_enabled":
                            selectedOptions.add(new Item(R.drawable.mesa, "Mėsą"));
                            break;
                        case "valgyti_desreles_enabled":
                            selectedOptions.add(new Item(R.drawable.desreles, "Dešrelių"));
                            break;
                        case "valgyti_bulvytes_enabled":
                            selectedOptions.add(new Item(R.drawable.bulvytes, "Bulvyčių"));
                            break;
                        case "valgyti_ledu_enabled":
                            selectedOptions.add(new Item(R.drawable.ledai, "Ledų"));
                            break;
                        case "valgyti_guminuku_enabled":
                            selectedOptions.add(new Item(R.drawable.guminukai, "Guminukų"));
                            break;
                        case "valgyti_pica_enabled":
                            selectedOptions.add(new Item(R.drawable.pica, "Picą"));
                            break;
                        case "valgyti_makaronu_enabled":
                            selectedOptions.add(new Item(R.drawable.makaronai, "Makaronų"));
                            break;
                        case "valgyti_kiausiniu_enabled":
                            selectedOptions.add(new Item(R.drawable.kiausiniai, "Kiaušinių"));
                            break;
                        case "valgyti_salotu_enabled":
                            selectedOptions.add(new Item(R.drawable.salotos, "Salotų"));
                            break;
                        case "valgyti_cipsu_enabled":
                            selectedOptions.add(new Item(R.drawable.traskuciai, "Čipsų"));
                            break;
                        case "valgyti_troskini_enabled":
                            selectedOptions.add(new Item(R.drawable.drugelis, "Troškinį"));
                            break;
                        case "valgyti_trapuciu_enabled":
                            selectedOptions.add(new Item(R.drawable.traputis, "Trapučių"));
                            break;
                    }
                    break;
                case "Noriu":
                    switch (option) {
                        case "noriu_sokineti_enabled":
                            selectedOptions.add(new Item(R.drawable.batutas, "Šokinėti"));
                            break;
                        case "noriu_ciuozineti_enabled":
                            selectedOptions.add(new Item(R.drawable.ciusti, "Čiuožinėti"));
                            break;
                        case "noriu_laistyti_enabled":
                            selectedOptions.add(new Item(R.drawable.laistyti, "Laistyti"));
                            break;
                        case "noriu_maudytis_enabled":
                            selectedOptions.add(new Item(R.drawable.maudytis, "Maudytis"));
                            break;
                        case "noriu_persirengti_enabled":
                            selectedOptions.add(new Item(R.drawable.persirengti, "Persirengti"));
                            break;
                        case "noriu_kartu_paguleti_enabled":
                            selectedOptions.add(new Item(R.drawable.lova, "Kartu pagulėti"));
                            break;
                        case "noriu_dusas_enabled":
                            selectedOptions.add(new Item(R.drawable.dusas, "Į dušą"));
                            break;
                        case "noriu_tualetas_enabled":
                            selectedOptions.add(new Item(R.drawable.tualetas, "Į tualetą"));
                            break;
                        case "noriu_miegoti_enabled":
                            selectedOptions.add(new Item(R.drawable.miegoti, "Miegoti"));
                            break;
                    }
                    break;
                case "Gerti":
                    switch (option) {
                        case "gerti_sultciu_enabled":
                            selectedOptions.add(new Item(R.drawable.sultys, "Sultčių"));
                            break;
                        case "gerti_citrinu_vandens_enabled":
                            selectedOptions.add(new Item(R.drawable.citrinu, "Citrinų vandens"));
                            break;
                        case "gerti_vandens_enabled":
                            selectedOptions.add(new Item(R.drawable.vanduo, "Vandens"));
                            break;
                        case "gerti_giros_enabled":
                            selectedOptions.add(new Item(R.drawable.drugelis, "Giros"));
                            break;
                        case "gerti_limonado_enabled":
                            selectedOptions.add(new Item(R.drawable.limonadas, "Limonado"));
                            break;
                    }
                    break;
                case "As":
                    switch (option) {
                        case "as_pavarges_enabled":
                            selectedOptions.add(new Item(R.drawable.pavarges, "Pavargęs"));
                            break;
                        case "as_piktas_enabled":
                            selectedOptions.add(new Item(R.drawable.piktas, "Piktas"));
                            break;
                        case "as_bijau_enabled":
                            selectedOptions.add(new Item(R.drawable.bijau, "Bijau"));
                            break;
                        case "as_liudnas_enabled":
                            selectedOptions.add(new Item(R.drawable.liudnas, "Liūdnas"));
                            break;
                        case "as_dziaugiuosi_enabled":
                            selectedOptions.add(new Item(R.drawable.laimingas, "Džiaugiuosi"));
                            break;
                        case "as_alkanas_enabled":
                            selectedOptions.add(new Item(R.drawable.alkanas, "Alkanas"));
                            break;
                    }
                    break;
                case "Skauda":
                    switch (option) {
                        case "skauda_galva_enabled":
                            selectedOptions.add(new Item(R.drawable.galva, "Galvą"));
                            break;
                        case "skauda_dantuka_enabled":
                            selectedOptions.add(new Item(R.drawable.dantis, "Dantuką"));
                            break;
                        case "skauda_akyte_enabled":
                            selectedOptions.add(new Item(R.drawable.aki, "Akytę"));
                            break;
                        case "skauda_gerkle_enabled":
                            selectedOptions.add(new Item(R.drawable.gerkle, "Gerklę"));
                            break;
                        case "skauda_pilvuka_enabled":
                            selectedOptions.add(new Item(R.drawable.pilva, "Pilvuką"));
                            break;
                        case "skauda_ranka_enabled":
                            selectedOptions.add(new Item(R.drawable.ranka, "Ranką"));
                            break;
                        case "skauda_koja_enabled":
                            selectedOptions.add(new Item(R.drawable.koja, "Koją"));
                            break;
                    }
                    break;
                default:
                    Log.d("MainActivity", "Unknown category: " + category);
                    break;
            }
        }
        return selectedOptions;
    }

    private void handleOption(String message) {
        speakText(message);
    }

    private void speakText(String text) {
        if (tts != null) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    private void handleSecondaryButtonClick(Item selectedItem) {
        optionLayout.removeAllViews();
        recyclerView.setVisibility(View.GONE);
        List<Item> selectedItems = new ArrayList<>();
        selectedItems.add(selectedItem);
        showSelectedOption(selectedItems);
        speakText(selectedItem.getText());
    }

    private void showSelectedOption(List<Item> selectedItems) {
        optionLayout.removeAllViews();
        RecyclerView selectedRecyclerView = new RecyclerView(this);
        selectedRecyclerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        selectedRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        CustomAdapter selectedAdapter = new CustomAdapter(selectedItems, this, new CustomAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Item item) {
                speakText(item.getText());
            }
        });
        selectedRecyclerView.setAdapter(selectedAdapter);
        optionLayout.addView(selectedRecyclerView);
    }

    private void showSecondaryOptions(List<Item> items) {
        optionLayout.removeAllViews();
        RecyclerView secondaryRecyclerView = new RecyclerView(this);
        secondaryRecyclerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        secondaryRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        CustomAdapter secondaryAdapter = new CustomAdapter(items, this, new CustomAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Item item) {
                handleSecondaryButtonClick(item);
            }
        });
        secondaryRecyclerView.setAdapter(secondaryAdapter);
        optionLayout.addView(secondaryRecyclerView);
    }

    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
}
