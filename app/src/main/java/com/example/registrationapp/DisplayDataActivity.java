package com.example.registrationapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class DisplayDataActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private LinearLayout linearLayout;

    private static final int REQUEST_CALL_PERMISSION = 1;
    private String mobileNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);

        dbHelper = new DBHelper(this);
        linearLayout = findViewById(R.id.linearLayout);

        displayData();
    }

    private void displayData() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DBHelper.TABLE_NAME, null, null, null, null, null, null);

        if (cursor != null) {
            int nameIndex = cursor.getColumnIndex(DBHelper.COLUMN_NAME);
            int mobileIndex = cursor.getColumnIndex(DBHelper.COLUMN_MOBILE);

            if (nameIndex != -1 && mobileIndex != -1) {
                while (cursor.moveToNext()) {
                    String name = cursor.getString(nameIndex);
                    final String mobile = cursor.getString(mobileIndex);

                    TextView textView = new TextView(this);
                    textView.setText("Name: " + name + "\nMobile: " + mobile);
                    linearLayout.addView(textView);

                    Button callButton = new Button(this);
                    callButton.setText("Call " + name);
                    callButton.setOnClickListener(v -> {
                        mobileNumber = mobile;
                        makePhoneCall();
                    });
                    linearLayout.addView(callButton);
                }
            } else {
                // Handle the case where column indices are not found
                TextView errorTextView = new TextView(this);
                errorTextView.setText("Error: Column not found in database.");
                linearLayout.addView(errorTextView);
            }
            cursor.close();
        }
        db.close();
    }

    private void makePhoneCall() {
        if (ContextCompat.checkSelfPermission(DisplayDataActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(DisplayDataActivity.this,
                    new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PERMISSION);
        } else {
            startCall();
        }
    }

    private void startCall() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + mobileNumber));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            startActivity(callIntent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCall();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
