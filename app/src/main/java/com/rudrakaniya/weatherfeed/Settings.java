package com.rudrakaniya.weatherfeed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class Settings extends AppCompatActivity {
    ImageView backButton;
    TextView mThemeTV, mThemeNameTV;
    ConstraintLayout mDisplayOptionsTVBtn;
    ConstraintLayout mNotificationOptionsTVBtn;
    ConstraintLayout mFeedbackTVBtn;
    ConstraintLayout mSignOutTVBtn;
    TextView mNotificationTV, mNotificationTypeTV;
    TextView mProvideFeedbackTV;
    TextView mSignOutEmailTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        backButton = findViewById(R.id.backButton);
        mThemeTV = findViewById(R.id.themeTV);
        mThemeNameTV = findViewById(R.id.themeNameTV);
        mNotificationTV = findViewById(R.id.notificationTV);
        mNotificationTypeTV = findViewById(R.id.notificationTypeTV);
        mProvideFeedbackTV = findViewById(R.id.provideFeedbackTV);
        mSignOutEmailTV = findViewById(R.id.signOutEmailTV);
        mDisplayOptionsTVBtn = findViewById(R.id.displayOptionsTVBtn);
        mNotificationOptionsTVBtn = findViewById(R.id.notificationOptionsTVBtn);
        mFeedbackTVBtn = findViewById(R.id.feedbackTVBtn);
        mSignOutTVBtn = findViewById(R.id.signOutTVBtn);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent newIntent = new Intent(Settings.this, MainActivity.class);
//                newIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(newIntent);
                finish();
            }
        });

        mDisplayOptionsTVBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(findViewById(android.R.id.content), "Customizations adding soon!", Snackbar.LENGTH_SHORT).show();

            }
        });

        mNotificationOptionsTVBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(findViewById(android.R.id.content), "Customizations adding soon!", Snackbar.LENGTH_SHORT).show();

            }
        });

        mFeedbackTVBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(findViewById(android.R.id.content), "On it!!", Snackbar.LENGTH_SHORT).show();

            }
        });

        mSignOutTVBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(findViewById(android.R.id.content), "On it!!", Snackbar.LENGTH_SHORT).show();

            }
        });
    }
}