package com.example.triviaquiz.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Question {
    @SerializedName("category")
    private String category;

    @SerializedName("type")
    private String type;

    @SerializedName("difficulty")
    private String difficulty;

    @SerializedName("question")
    private String question;

    @SerializedName("correct_answer")
    private String correctAnswer;

    @SerializedName("incorrect_answers")
    private List<String> incorrectAnswers;

    // Getter methods for all attributes

    public String getCategory() {
        return category;
    }

    public String getType() {
        return type;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public List<String> getIncorrectAnswers() {
        return incorrectAnswers;
    }
}
