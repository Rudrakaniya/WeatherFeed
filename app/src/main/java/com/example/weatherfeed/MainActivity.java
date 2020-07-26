package com.example.weatherfeed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    MotionLayout mMotionLayout;
    ImageButton mImageButton;
    boolean isDrawerOpen = false;

    final String LOGCAT_TAG = "WeatherApp";

    final int REQUEST_CODE = 123;
    final int NEW_CITY_CODE = 456;

    final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather";
    final String APP_ID = "8ddd9e61267c0854ea44d8f0e349c731";

    final long MIN_TIME = 5000;
    final float MIN_DISTANCE = 1000;

    // Member Variables:
    boolean mUseLocation = true;
    TextView mCityLabel;
    ImageView mWeatherImage;
    TextView mTemperatureLabel;

    // Location provider
    final String LOCATION_PROVIDER = LocationManager.GPS_PROVIDER;
    LocationManager mLocationManager;
    LocationListener mLocationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Linking the elements in the layout to Java code.
        mCityLabel = findViewById(R.id.locationTV);
        mWeatherImage = findViewById(R.id.weatherSymbolIV);
        mTemperatureLabel = findViewById(R.id.tempTV);
        Button changeCityButton = findViewById(R.id.changeCityButton);

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

        // Add an OnClickListener to the changeCityButton here:
        changeCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, ChangeCityController.class);

                // Using startActivityForResult since we just get back the city name.
                // Providing an arbitrary request code to check against later.
                startActivityForResult(myIntent, NEW_CITY_CODE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("WeatherApp", "onResume() called");
        Intent myIntent = getIntent();
        String city = myIntent.getStringExtra("City");

        if (city != null){
            getWeatherForNewCity(city);
        }else
        {
            getWeatherForCurrentLocation();
        }

    }

    private void getWeatherForNewCity(String city) {
        Log.d(LOGCAT_TAG, "Getting weather for new city");
        RequestParams params = new RequestParams();
        params.put("q", city);
        params.put("appid", APP_ID);

        letsDoSomeNetworking(params);
    }

    private void getWeatherForCurrentLocation() {
        Log.d(LOGCAT_TAG, "Getting weather for current location");
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                Log.d(LOGCAT_TAG, "onLocationChanged() callback received");

                String longitude = String.valueOf(location.getLongitude());
                String latitude = String.valueOf(location.getLatitude());

                Log.d(LOGCAT_TAG, "longitude is: " + longitude);
                Log.d(LOGCAT_TAG, "latitude is: " + latitude);

                RequestParams params = new RequestParams();
                params.put("lat", latitude);
                params.put("lon", longitude);
                params.put("appid", APP_ID);
                letsDoSomeNetworking(params);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                // Log statements to help you debug your app.
                Log.d(LOGCAT_TAG, "onStatusChanged() callback received. Status: " + status);
                Log.d(LOGCAT_TAG, "2 means AVAILABLE, 1: TEMPORARILY_UNAVAILABLE, 0: OUT_OF_SERVICE");
            }

            @Override
            public void onProviderEnabled(String provider) {
                Log.d(LOGCAT_TAG, "onProviderEnabled() callback received. Provider: " + provider);
            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.d(LOGCAT_TAG, "onProviderDisabled() callback received. Provider: " + provider);
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }

        // Some additional log statements to help you debug
        Log.d(LOGCAT_TAG, "Location Provider used: "
                + mLocationManager.getProvider(LOCATION_PROVIDER).getName());
        Log.d(LOGCAT_TAG, "Location Provider is enabled: "
                + mLocationManager.isProviderEnabled(LOCATION_PROVIDER));
        Log.d(LOGCAT_TAG, "Last known location (if any): "
                + mLocationManager.getLastKnownLocation(LOCATION_PROVIDER));
        Log.d(LOGCAT_TAG, "Requesting location updates");

        mLocationManager.requestLocationUpdates(LOCATION_PROVIDER, MIN_TIME, MIN_DISTANCE, mLocationListener);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("WeatherApp", "onRequestPermissionsResult(): Permission granted!");
                getWeatherForCurrentLocation();
            } else {
                Log.d("WeatherApp", "Permission denied =( ");
            }
        }

    }

    private void letsDoSomeNetworking(RequestParams params) {

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(WEATHER_URL, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d(LOGCAT_TAG, "Success! JSON: " + response.toString());
                WeatherDataModel weatherData = WeatherDataModel.fromJson(response);
                updateUI(weatherData);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {

                Log.e(LOGCAT_TAG, "Fail " + e.toString());
                Toast.makeText(MainActivity.this, "Request Failed", Toast.LENGTH_SHORT).show();

                Log.d(LOGCAT_TAG, "Status code " + statusCode);
                Log.d(LOGCAT_TAG, "Here's what we got instead " + response.toString());
            }

        });
    }

    private void updateUI(WeatherDataModel weather) {
        mTemperatureLabel.setText(weather.getTemperature());
        mCityLabel.setText(weather.getCity());

        int resourceID = getResources().getIdentifier(weather.getIconName(), "drawable", getPackageName());
        mWeatherImage.setImageResource(resourceID);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mLocationManager != null) mLocationManager.removeUpdates(mLocationListener);
    }


    @Override
    public void onBackPressed() {
        if (!isDrawerOpen) {
            super.onBackPressed();
        } else {
            mMotionLayout.transitionToStart();
            isDrawerOpen = false;
        }

    }
}