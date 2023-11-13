package com.example.triviaquiz.models;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
        @GET("api.php")
        Call<ApiResponse> getQuestions(@Query("amount") int amount);


}
