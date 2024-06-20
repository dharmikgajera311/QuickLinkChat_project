package com.example.quicklinkchat;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class Local extends AppCompatActivity {
    public static final String[] languages = {"Select Language", "English", "Gujrati", "Hindi"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local);
        Spinner spinner = findViewById(R.id.languageSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, languages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedLang = adapterView.getItemAtPosition(i).toString();
                if (selectedLang.equals("English")) {
                    setLocale("en");
                    Intent intent = new Intent(Local.this, login.class);
                    startActivity(intent);
                } else if (selectedLang.equals("Gujrati")) {
                    setLocale("gu");
                    Intent intent = new Intent(Local.this, login.class);
                    startActivity(intent);
                } else if (selectedLang.equals("Hindi")) {
                    setLocale("hi");
                    Intent intent = new Intent(Local.this, login.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Button confirmButton = findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Local.this, login.class);
                startActivity(intent);
                Toast.makeText(Local.this, "working", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setLocale(String langCode) {
        new Thread(() -> {
            Locale locale = new Locale(langCode);
            Locale.setDefault(locale);
            Resources resources = getResources();
            Configuration config = resources.getConfiguration();
            config.setLocale(locale);
            resources.updateConfiguration(config, resources.getDisplayMetrics());
            runOnUiThread(() -> recreate());
        }).start();
    }
}
