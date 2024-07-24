package com.example.registrationapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchDataActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private LinearLayout contentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_data);

        progressBar = findViewById(R.id.progressBar);
        contentLayout = findViewById(R.id.contentLayout);

        // Start AsyncTask to fetch data
        FetchDataTask fetchDataTask = new FetchDataTask();
        fetchDataTask.execute("https://jsonplaceholder.typicode.com/posts");
    }

    private class FetchDataTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String urlString = strings[0];
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(urlString);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder builder = new StringBuilder();
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line).append("\n");
                }
                return builder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.GONE); // Hide progress bar
            contentLayout.setVisibility(View.VISIBLE); // Show content layout

            // Process fetched data and update UI
            if (s != null) {
                try {
                    JSONArray jsonArray = new JSONArray(s);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String title = jsonObject.getString("title");
                        String body = jsonObject.getString("body");

                        // Create a content block for each item
                        addContentBlock(title, body);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        private void addContentBlock(String title, String body) {
            // Create a new content block
            View contentBlock = getLayoutInflater().inflate(R.layout.content_block, contentLayout, false);

            // Set title (bold) and body
            TextView titleTextView = contentBlock.findViewById(R.id.titleTextView);
            titleTextView.setText(title);

            TextView bodyTextView = contentBlock.findViewById(R.id.bodyTextView);
            bodyTextView.setText(body);

            // Add to content layout with separator
            contentLayout.addView(contentBlock);
            addSeparator();
        }

        private void addSeparator() {
            View separator = new View(FetchDataActivity.this);
            separator.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    2 // Height of separator
            ));
            separator.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
            contentLayout.addView(separator);
        }
    }
}
