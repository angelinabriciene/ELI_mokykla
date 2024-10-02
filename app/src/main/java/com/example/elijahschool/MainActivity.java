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
        itemList.add(new Item(R.drawable.as, "AŠ NORIU"));
        itemList.add(new Item(R.drawable.man, "MAN"));
        itemList.add(new Item(R.drawable.reikia, "REIKIA"));
        itemList.add(new Item(R.drawable.negalima, "NEGALIMA"));
        itemList.add(new Item(R.drawable.daiktai, "DAIKTAI"));
        itemList.add(new Item(R.drawable.maistas, "MAISTAS"));
        itemList.add(new Item(R.drawable.kuno, "KŪNO DALYS"));


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
            case "AŠ NORIU":
                handleOption("AŠ NORIU");
                secondaryOptions = getSelectedOptions("AS_NORIU");
                break;
            case "MAN":
                handleOption("MAN");
                secondaryOptions = getSelectedOptions("MAN");
                break;
            case "REIKIA":
                handleOption("REIKIA");
                secondaryOptions = getSelectedOptions("REIKIA");
                break;
            case "NEGALIMA":
                handleOption("NEGALIMA");
                secondaryOptions = getSelectedOptions("NEGALIMA");
                break;
            case "DAIKTAI":
                handleOption("DAIKTAI");
                secondaryOptions = getSelectedOptions("DAIKTAI");
                break;
            case "MAISTAS":
                handleOption("MAISTAS");
                secondaryOptions = getSelectedOptions("MAISTAS");
                break;
            case "KŪNO DALYS":
                handleOption("KŪNO DALYS");
                secondaryOptions = getSelectedOptions("KUNO_DALYS");
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
                case "AS_NORIU":
                    switch (option) {
                        case "AS_NORIU_VALGYTI_enabled":
                            selectedOptions.add(new Item(R.drawable.valgyti, "VALGYTI"));
                            break;
                        case "AS_NORIU_ZAISTI_enabled":
                            selectedOptions.add(new Item(R.drawable.zaisti, "ŽAISTI"));
                            break;
                        case "AS_NORIU_MIEGOTI_enabled":
                            selectedOptions.add(new Item(R.drawable.miegoti, "MIEGOTI"));
                            break;
                        case "AS_NORIU_PIESTI_enabled":
                            selectedOptions.add(new Item(R.drawable.piesti, "PIEŠTI"));
                            break;
                        case "AS_NORIU_PRAUSTIS_enabled":
                            selectedOptions.add(new Item(R.drawable.praustis, "PRAUSTIS"));
                            break;
                        case "AS_NORIU_I_LAUKĄ_enabled":
                            selectedOptions.add(new Item(R.drawable.lauka, "Į LAUKĄ"));
                            break;
                        case "AS_NORIU_PRISIGLAUSTI_enabled":
                            selectedOptions.add(new Item(R.drawable.prisiglausti, "PRISIGLAUSTI"));
                            break;
                        case "AS_NORIU_SOKINETI_enabled":
                            selectedOptions.add(new Item(R.drawable.sokineti, "ŠOKINĖTI"));
                            break;
                        case "AS_NORIU_ZIURETI_TELEVIZORIU_enabled":
                            selectedOptions.add(new Item(R.drawable.televizoriu, "ŽIŪRĖTI TELEVIZORIŲ"));
                            break;
                        case "AS_NORIU_PERSIRENGTI_enabled":
                            selectedOptions.add(new Item(R.drawable.persirengti, "PERSIRENGTI"));
                            break;
                        case "AS_NORIU_PASAKOS_enabled":
                            selectedOptions.add(new Item(R.drawable.pasakos, "PASAKOS"));
                            break;
                        case "AS_NORIU_I_TUALETA_enabled":
                            selectedOptions.add(new Item(R.drawable.tualeta, "Į TUALETĄ"));
                            break;
                        case "AS_NORIU_DRAUGAUTI_enabled":
                            selectedOptions.add(new Item(R.drawable.draugauti, "DRAUGAUTI"));
                            break;
                    }
                    break;
                case "MAN":
                    switch (option) {
                        case "MAN_SKAUDA_enabled":
                            selectedOptions.add(new Item(R.drawable.skauda, "SKAUDA"));
                            break;
                        case "MAN_LIUDNA_enabled":
                            selectedOptions.add(new Item(R.drawable.liudna, "LIŪDNA"));
                            break;
                        case "MAN_NUOBODU_enabled":
                            selectedOptions.add(new Item(R.drawable.nuobodu, "NUOBODU"));
                            break;
                        case "MAN_BAISU_enabled":
                            selectedOptions.add(new Item(R.drawable.baisu, "BAISU"));
                            break;
                        case "MAN_SUNKU_SUPRASTI_enabled":
                            selectedOptions.add(new Item(R.drawable.suprasti, "SUNKU SUPRASTI"));
                            break;
                        case "MAN_PER_SVIESU_enabled":
                            selectedOptions.add(new Item(R.drawable.sviesu, "PER ŠVIESU"));
                            break;
                        case "MAN_PER_GARSIAI_enabled":
                            selectedOptions.add(new Item(R.drawable.garsiai, "PER GARSIAI"));
                            break;
                        case "MAN_NEPATOGU_enabled":
                            selectedOptions.add(new Item(R.drawable.nepatogu, "NEPATOGU"));
                            break;
                        case "MAN_KARSTA_enabled":
                            selectedOptions.add(new Item(R.drawable.karsta, "KARŠTA"));
                            break;
                        case "MAN_SALTA_enabled":
                            selectedOptions.add(new Item(R.drawable.salta, "ŠALTA"));
                            break;
                        case "MAN_SLAPIA_enabled":
                            selectedOptions.add(new Item(R.drawable.slapia, "ŠLAPIA"));
                            break;
                    }
                    break;
                case "REIKIA":
                    switch (option) {
                        case "REIKIA_PERSIRENGTI_enabled":
                            selectedOptions.add(new Item(R.drawable.persirengti, "PERSIRENGTI"));
                            break;
                        case "REIKIA_MIEGOTI_enabled":
                            selectedOptions.add(new Item(R.drawable.miegoti, "MIEGOTI"));
                            break;
                        case "REIKIA_SKAITYTI_enabled":
                            selectedOptions.add(new Item(R.drawable.pasakos, "SKAITYTI"));
                            break;
                        case "REIKIA_VALGYTI_enabled":
                            selectedOptions.add(new Item(R.drawable.valgyti, "VALGYTI"));
                            break;
                        case "REIKIA_MOKYTIS_enabled":
                            selectedOptions.add(new Item(R.drawable.mokytis, "MOKYTIS"));
                            break;
                        case "REIKIA_ZAISTI_enabled":
                            selectedOptions.add(new Item(R.drawable.zaisti, "ŽAISTI"));
                            break;
                        case "REIKIA_SUSITVARKYTI_enabled":
                            selectedOptions.add(new Item(R.drawable.tvarkytis, "SUSITVARKYTI"));
                            break;
                        case "REIKIA_GERTI_VAISTUS_enabled":
                            selectedOptions.add(new Item(R.drawable.vaistai, "GERTI VAISTUS"));
                            break;
                        case "REIKIA_PRAUSTIS_enabled":
                            selectedOptions.add(new Item(R.drawable.praustis, "PRAUSTIS"));
                            break;
                        case "REIKIA_PALAUKTI_enabled":
                            selectedOptions.add(new Item(R.drawable.palaukti, "PALAUKTI"));
                            break;
                        case "REIKIA_BUTI_TYLIAI_enabled":
                            selectedOptions.add(new Item(R.drawable.tyliai, "BŪTI TYLIAI"));
                            break;
                        case "REIKIA_ISVALYTI_enabled":
                            selectedOptions.add(new Item(R.drawable.isvalyti, "IŠVALYTI"));
                            break;
                        case "REIKIA_RASYTI_enabled":
                            selectedOptions.add(new Item(R.drawable.rasyti, "RAŠYTI"));
                            break;
                        case "REIKIA_KLAUSYTIS_enabled":
                            selectedOptions.add(new Item(R.drawable.klausyti, "KLAUSYTIS"));
                            break;
                        case "REIKIA_I_TUALETA_enabled":
                            selectedOptions.add(new Item(R.drawable.tualeta, "Į TUALETĄ"));
                            break;
                        case "REIKIA_SUKUOTIS_enabled":
                            selectedOptions.add(new Item(R.drawable.sukuotis, "ŠUKUOTIS"));
                            break;
                    }
                    break;
                case "NEGALIMA":
                    switch (option) {
                        case "NEGALIMA_MUSTIS_enabled":
                            selectedOptions.add(new Item(R.drawable.mustis, "MUŠTIS"));
                            break;
                        case "NEGALIMA_SPJAUDYTI_enabled":
                            selectedOptions.add(new Item(R.drawable.spjaudyti, "SPJAUDYTI"));
                            break;
                        case "NEGALIMA_METYTI_enabled":
                            selectedOptions.add(new Item(R.drawable.metyti, "MĖTYTI"));
                            break;
                        case "NEGALIMA_ISEITI_enabled":
                            selectedOptions.add(new Item(R.drawable.iseiti, "IŠEITI"));
                            break;
                        case "NEGALIMA_SIUKSLINTI_enabled":
                            selectedOptions.add(new Item(R.drawable.siukslinti, "ŠIUKŠLINTI"));
                            break;
                        case "NEGALIMA_BEGTI_enabled":
                            selectedOptions.add(new Item(R.drawable.pabegti, "BĖGTI"));
                            break;
                    }
                    break;
                case "DAIKTAI":
                    switch (option) {
                        case "DAIKTAI_LOVA_enabled":
                            selectedOptions.add(new Item(R.drawable.lova, "LOVA"));
                            break;
                        case "DAIKTAI_KEDE_enabled":
                            selectedOptions.add(new Item(R.drawable.kede, "KĖDĖ"));
                            break;
                        case "DAIKTAI_KNYGA_enabled":
                            selectedOptions.add(new Item(R.drawable.knyga, "KNYGA"));
                            break;
                        case "DAIKTAI_ZAISLAI_enabled":
                            selectedOptions.add(new Item(R.drawable.zaislai, "ŽAISLAI"));
                            break;
                        case "DAIKTAI_BATAI_enabled":
                            selectedOptions.add(new Item(R.drawable.batai, "BATAI"));
                            break;
                        case "DAIKTAI_KELNES_enabled":
                            selectedOptions.add(new Item(R.drawable.kelnes, "KELNĖS"));
                            break;
                        case "DAIKTAI_KEPURE_enabled":
                            selectedOptions.add(new Item(R.drawable.kepure, "KEPURĖ"));
                            break;
                        case "DAIKTAI_STRIUKE_enabled":
                            selectedOptions.add(new Item(R.drawable.striuke, "STRIUKĖ"));
                            break;
                        case "DAIKTAI_KELNAITES_enabled":
                            selectedOptions.add(new Item(R.drawable.kelnaites, "KELNAITĖS"));
                            break;
                        case "DAIKTAI_PIRSTINES_enabled":
                            selectedOptions.add(new Item(R.drawable.pirstines, "PIRŠTINĖS"));
                            break;
                    }
                    break;
                case "MAISTAS":
                    switch (option) {
                        case "MAISTAS_VAISIAI_enabled":
                            selectedOptions.add(new Item(R.drawable.vaisiai, "VAISIAI"));
                            break;
                        case "MAISTAS_DARZOVES_enabled":
                            selectedOptions.add(new Item(R.drawable.darzoves, "DARŽOVĖS"));
                            break;
                        case "MAISTAS_MESA_enabled":
                            selectedOptions.add(new Item(R.drawable.mesa, "MĖSA"));
                            break;
                        case "MAISTAS_KOSE_enabled":
                            selectedOptions.add(new Item(R.drawable.kose, "KOŠĖ"));
                            break;
                        case "MAISTAS_SRIUBA_enabled":
                            selectedOptions.add(new Item(R.drawable.sriuba, "SRIUBA"));
                            break;
                        case "MAISTAS_VANDUO_enabled":
                            selectedOptions.add(new Item(R.drawable.vanduo, "VANDUO"));
                            break;
                        case "MAISTAS_ARBATA_enabled":
                            selectedOptions.add(new Item(R.drawable.arbata, "ARBATA"));
                            break;
                        case "MAISTAS_PUSRYCIAI_enabled":
                            selectedOptions.add(new Item(R.drawable.pusryciai, "PUSRYČIAI"));
                            break;
                        case "MAISTAS_PIETUS_enabled":
                            selectedOptions.add(new Item(R.drawable.pietus, "PIETŪS"));
                            break;
                        case "MAISTAS_VAKARIENE_enabled":
                            selectedOptions.add(new Item(R.drawable.vakariene, "VAKARIENĖ"));
                            break;
                        case "MAISTAS_UZKANDZIAI_enabled":
                            selectedOptions.add(new Item(R.drawable.uzkandziai, "UŽKANDŽIAI"));
                            break;
                        case "MAISTAS_SULTYS_enabled":
                            selectedOptions.add(new Item(R.drawable.sultys, "SULTYS"));
                            break;
                    }
                    break;
                case "KUNO_DALYS":
                    switch (option) {
                        case "KUNO_DALYS_GALVA_enabled":
                            selectedOptions.add(new Item(R.drawable.galva, "GALVA"));
                            break;
                        case "KUNO_DALYS_VEIDAS_enabled":
                            selectedOptions.add(new Item(R.drawable.veidas, "VEIDAS"));
                            break;
                        case "KUNO_DALYS_AUSYS_enabled":
                            selectedOptions.add(new Item(R.drawable.ausis, "AUSYS"));
                            break;
                        case "KUNO_DALYS_AKYS_enabled":
                            selectedOptions.add(new Item(R.drawable.akys, "AKYS"));
                            break;
                        case "KUNO_DALYS_NOSIS_enabled":
                            selectedOptions.add(new Item(R.drawable.nosis, "NOSIS"));
                            break;
                        case "KUNO_DALYS_PLAUKAI_enabled":
                            selectedOptions.add(new Item(R.drawable.plaukai, "PLAUKAI"));
                            break;
                        case "KUNO_DALYS_LUPOS_enabled":
                            selectedOptions.add(new Item(R.drawable.lupos, "LŪPOS"));
                            break;
                        case "KUNO_DALYS_DANTYS_enabled":
                            selectedOptions.add(new Item(R.drawable.dantys, "DANTYS"));
                            break;
                        case "KUNO_DALYS_BURNA_enabled":
                            selectedOptions.add(new Item(R.drawable.burna, "BURNA"));
                            break;
                        case "KUNO_DALYS_KAKLAS_enabled":
                            selectedOptions.add(new Item(R.drawable.kaklas, "KAKLAS"));
                            break;
                        case "KUNO_DALYS_NUGARA_enabled":
                            selectedOptions.add(new Item(R.drawable.nugara, "NUGARA"));
                            break;
                        case "KUNO_DALYS_PECIAI_enabled":
                            selectedOptions.add(new Item(R.drawable.peciai, "PEČIAI"));
                            break;
                        case "KUNO_DALYS_RANKA_enabled":
                            selectedOptions.add(new Item(R.drawable.ranka, "RANKA"));
                            break;
                        case "KUNO_DALYS_PIRSTAI_enabled":
                            selectedOptions.add(new Item(R.drawable.pirstai, "PIRŠTAI"));
                            break;
                        case "KUNO_DALYS_KOJA_enabled":
                            selectedOptions.add(new Item(R.drawable.kojos, "KOJA"));
                            break;
                        case "KUNO_DALYS_PEDA_enabled":
                            selectedOptions.add(new Item(R.drawable.pedos, "PĖDA"));
                            break;
                        case "KUNO_DALYS_PILVAS_enabled":
                            selectedOptions.add(new Item(R.drawable.pilvas, "PILVAS"));
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
            tts.speak(text.toLowerCase(new Locale("lt", "LT")), TextToSpeech.QUEUE_FLUSH, null, null);
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
