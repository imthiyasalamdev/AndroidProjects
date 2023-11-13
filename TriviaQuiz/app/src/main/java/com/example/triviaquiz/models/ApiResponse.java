package com.example.triviaquiz.models;

import com.example.triviaquiz.models.Question;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ApiResponse {
        @SerializedName("results")
        private List<Question> questions;

        public List<Question> getQuestions() {
            return questions;

}}
