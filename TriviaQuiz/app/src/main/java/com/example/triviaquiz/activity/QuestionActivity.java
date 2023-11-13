package com.example.triviaquiz.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.triviaquiz.R;
import com.example.triviaquiz.models.ApiClient;
import com.example.triviaquiz.models.ApiResponse;
import com.example.triviaquiz.models.ApiService;
import com.example.triviaquiz.models.Question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionActivity extends AppCompatActivity {

    private List<Question> questionList;
    private int currentQuestionIndex = 0;
    private int score = 0;

    private TextView questionTextView;
    private RadioGroup optionsRadioGroup;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        questionTextView = findViewById(R.id.questionTextView);
        optionsRadioGroup = findViewById(R.id.optionsRadioGroup);
        submitButton = findViewById(R.id.submitButton);

        loadQuestions();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });
    }

    private void loadQuestions() {
        ApiService apiService = ApiClient.getApiService();

        if (apiService != null) {
            Call<ApiResponse> call1 = apiService.getQuestions(10);

            call1.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.isSuccessful()) {
                        ApiResponse apiResponse = response.body();
                        questionList = apiResponse.getQuestions();
                        showNextQuestion();
                    } else {
                        Toast.makeText(QuestionActivity.this, "Error fetching questions", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(QuestionActivity.this, "Network error", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(QuestionActivity.this, "ApiService is null", Toast.LENGTH_SHORT).show();
        }
    }



    private void showNextQuestion() {
        if (currentQuestionIndex < questionList.size()) {
            Question question = questionList.get(currentQuestionIndex);

            questionTextView.setText(question.getQuestion());

            optionsRadioGroup.removeAllViews();
            List<String> options = new ArrayList<>(question.getIncorrectAnswers());
            options.add(question.getCorrectAnswer());
            Collections.shuffle(options);

            for (String option : options) {
                RadioButton radioButton = new RadioButton(this);
                radioButton.setText(option);
                optionsRadioGroup.addView(radioButton);
            }

            currentQuestionIndex++;
        } else {
            showResult();
        }
    }

    private void checkAnswer() {
        int selectedRadioButtonId = optionsRadioGroup.getCheckedRadioButtonId();
        if (selectedRadioButtonId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
            String selectedAnswer = selectedRadioButton.getText().toString();

            Question question = questionList.get(currentQuestionIndex - 1);
            String correctAnswer = question.getCorrectAnswer();

            if (selectedAnswer.equals(correctAnswer)) {
                score++;
            }

            showNextQuestion();
        }
    }

    private void showResult() {
        Intent intent = new Intent(QuestionActivity.this, ResultActivity.class);
        intent.putExtra("score", score);
        intent.putExtra("totalQuestions", questionList.size());
        startActivity(intent);
        finish();
    }
}
