package com.example.newsaggregator.model;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NewsApiClient {
    private NewsApiService newsApiService;

    public NewsApiClient() {
        newsApiService = RetrofitClient.getClient().create(NewsApiService.class);
    }

    public void fetchNewsData(String query, Callback<NewsResponse> callback) {
        Call<NewsResponse> call = newsApiService.getNews(query, "0361ca6705db4b439e8e931e87ee4819");
        call.enqueue(callback);
    }
}
