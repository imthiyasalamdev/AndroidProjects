package com.example.triviaquiz.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.triviaquiz.R;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView logoImageView = findViewById(R.id.logoImageView);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        logoImageView.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Animation start, you can perform any initialization here
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Animation end, navigate to SignupActivity
                startActivity(new Intent(SplashActivity.this, SignupActivity.class));
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Animation repeat
            }
        });
    }
}

