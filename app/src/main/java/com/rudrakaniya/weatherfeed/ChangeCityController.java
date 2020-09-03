package com.rudrakaniya.weatherfeed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ChangeCityController extends AppCompatActivity {
    EditText editTextField;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_city_layout);

        editTextField = findViewById(R.id.queryET);
        ImageView backButton = findViewById(R.id.backButton);

     sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newCity = editTextField.getText().toString();
                Intent newCityIntent = new Intent(ChangeCityController.this, MainActivity.class);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("cityName", newCity);
                editor.commit();
                // Adds what was entered in the EditText as an extra to the intent.
                newCityIntent.putExtra("City", newCity);

                // We started this activity for a result, so now we are setting the result.
                setResult(2, newCityIntent);
                finish();
            }
        });

        // Buttons can have a listener for clicks.
        // EditTexts can have listeners for keyboard presses like hitting the enter key.
        editTextField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                String newCity = editTextField.getText().toString();

                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("cityName", newCity);
                editor.commit();

                Intent newCityIntent = new Intent(ChangeCityController.this, MainActivity.class);

                // Adds what was entered in the EditText as an extra to the intent.
                newCityIntent.putExtra("City", newCity);

                // We started this activity for a result, so now we are setting the result.
                setResult(2, newCityIntent);
                Log.d("WeatherApp", "  City name after EditText is = " + newCity);

                // This destroys the ChangeCityController.
                finish();
                return true;
            }
        });

    }

    @Override
    public void onBackPressed() {
        String newCity = editTextField.getText().toString();
        Intent newCityIntent = new Intent(ChangeCityController.this, MainActivity.class);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("cityName", newCity);
        editor.commit();
        // Adds what was entered in the EditText as an extra to the intent.
        newCityIntent.putExtra("City", newCity);

        // We started this activity for a result, so now we are setting the result.
        setResult(2, newCityIntent);
        super.onBackPressed();
    }
}