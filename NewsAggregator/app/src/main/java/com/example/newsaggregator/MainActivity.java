package com.example.newsaggregator;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsaggregator.NewsAdapter;
import com.example.newsaggregator.R;
import com.example.newsaggregator.model.Article;
import com.example.newsaggregator.model.NewsApiClient;
import com.example.newsaggregator.model.NewsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView newsRecyclerView;
    private NewsAdapter newsAdapter;
    private NewsApiClient newsApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsRecyclerView = findViewById(R.id.newsRecyclerView);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        newsApiClient = new NewsApiClient();

        fetchNewsData();
    }

    private void fetchNewsData() {
        newsApiClient.fetchNewsData("technology", new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful()) {
                    List<Article> articles = response.body().getArticles();
                    newsAdapter = new NewsAdapter(articles);
                    newsRecyclerView.setAdapter(newsAdapter);
                } else {
                    // Handle error
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                // Handle failure
            }
        });
    }
}
