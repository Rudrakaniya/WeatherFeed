package com.rudrakaniya.weatherfeed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

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

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL  , new String[] { "me@somewhere.com" });
                intent.putExtra(Intent.EXTRA_SUBJECT, "My subject");
                intent.putExtra(Intent.EXTRA_TEXT, "My subject");
                startActivity(Intent.createChooser(intent, "Email via..."));

            }
        });

        mSignOutTVBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(findViewById(android.R.id.content), "On it!!", Snackbar.LENGTH_SHORT).show();
                AuthUI.getInstance().signOut(getApplicationContext())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @SuppressLint("WrongConstant")
                            @Override
                            public void onSuccess(Void aVoid) {
                                // GOTO SignIn activity
                                Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                                    intent.addFlags(0x8000); // equal to Intent.FLAG_ACTIVITY_CLEAR_TASK which is only available from API level 11
                                startActivity(intent);
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Snackbar.make(findViewById(android.R.id.content), "Failed to Sign Out", Snackbar.LENGTH_SHORT).show();
                            }
                        });


            }
        });
    }
}