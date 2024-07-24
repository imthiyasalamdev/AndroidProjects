package com.example.registrationapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button registerButton, displayDataButton, fetchDataButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerButton = findViewById(R.id.registerButton);
        displayDataButton = findViewById(R.id.displayDataButton);
        fetchDataButton = findViewById(R.id.fetchDataButton);

        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        displayDataButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DisplayDataActivity.class);
            startActivity(intent);
        });

        fetchDataButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FetchDataActivity.class);
            startActivity(intent);
        });
    }
}
