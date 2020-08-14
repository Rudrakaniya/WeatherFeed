package com.example.weatherfeed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Settings extends AppCompatActivity {
    ImageView backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent newIntent = new Intent(Settings.this, MainActivity.class);
//                newIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(newIntent);
                finish();
            }
        });
    }
}