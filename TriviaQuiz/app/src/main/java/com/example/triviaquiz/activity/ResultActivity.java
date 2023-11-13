package com.example.triviaquiz.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.triviaquiz.R;

public class ResultActivity extends AppCompatActivity {
    public static final String EXTRA_SCORE = "extra_score";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        int score = getIntent().getIntExtra(EXTRA_SCORE, 0);

        TextView scoreTextView = findViewById(R.id.scoreTextView);
        scoreTextView.setText(getString(R.string.result_score, score));
    }
}