package com.example.weatherfeed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    MotionLayout mMotionLayout;
    ImageButton mImageButton;
    boolean isDrawerOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMotionLayout = findViewById(R.id.motion_layout);
        mImageButton = findViewById(R.id.img);

        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isDrawerOpen) {
                    mMotionLayout.transitionToEnd();
                    isDrawerOpen = true;
                } else {
                    mMotionLayout.transitionToStart();
                    isDrawerOpen = false;
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (!isDrawerOpen) {
            super.onBackPressed();
            return;
        }
        mMotionLayout.transitionToStart();
        isDrawerOpen = false;
    }
}