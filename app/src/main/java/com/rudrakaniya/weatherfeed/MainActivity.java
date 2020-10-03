package com.rudrakaniya.weatherfeed;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.google.android.material.progressindicator.ProgressIndicator;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    MotionLayout mMotionLayout;
    ImageButton mImageButton;
    boolean isDrawerOpen = false;

    private String LOCATION_LATITUDE, LOCATION_LONGITUDE;
    private long CURRENT_TIME, CURRENT_TIMEZONE;

    final String LOGCAT_TAG = "WeatherApp";

    final int REQUEST_CODE = 123;
    final int NEW_CITY_CODE = 2;

    final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather";
    final String APP_ID = "8ddd9e61267c0854ea44d8f0e349c731";
    final String IMAGE_LOAD_LINK = "http://openweathermap.org/img/wn/";
//    10d@2x.png

    final String ONE_CALL_API_URL = "https://api.openweathermap.org/data/2.5/onecall";

    final long MIN_TIME = 5000;
    final float MIN_DISTANCE = 1000;

    // Constraint layout
    ConstraintLayout mScrollViewConstraintLayout;

    //Progress bar
    TextView mProgressBar;

    // ImageSlider
    // Image Slider Using https://github.com/smarteist/Android-Image-Slider
    SliderView sliderView;
    private SliderAdapterExample adapter;
    private int SLIDER_COUNT = 4;

    // Sunset, Sunrise layout
    TextView mSunLayoutTV;

    // Navigation drawer
    TextView mSettingsNav;
    TextView mAboutUsNav;

    // Member Variables:
    boolean mUseLocation = true;
    TextView mCityLabel;
    ImageView mWeatherImage;
    TextView mTemperatureLabel;
    TextView mDateTextView, mDayTextView;
    TextView mCurrentWeatherMainTV;
    TextView mCurrentWeatherDescriptionTV;

    //Weather Forecast layout
    TextView mWeekDay1TV, mWeekDay2TV, mWeekDay3TV, mWeekDay4TV, mWeekDay5TV;
    ImageView mWeekDay1IV, mWeekDay2IV, mWeekDay3IV, mWeekDay4IV, mWeekDay5IV;
    TextView mWeekDay1TempTV, mWeekDay2TempTV, mWeekDay3TempTV, mWeekDay4TempTV, mWeekDay5TempTV;

    //Sunset sunrise layout
    TextView mSunriseTimeTextView, mSunsetTimeTextView;

    //Weather Quote layout
    TextView mWeatherQuoteTV;

    //Detailed view layout
    TextView mDetailViewLocationTV, mDetailViewTempTV, mWindSpeedTV, mHumidityPercentTV, mPressureHPATV, mCloudinessTV, mUvIndexTV, mVisibilityRangeTV;

    // Location provider
    final String LOCATION_PROVIDER = LocationManager.GPS_PROVIDER;
    LocationManager mLocationManager;
    LocationListener mLocationListener;
    String city;
    Integer mProgressBar_Boo = 1;


    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Constraint layout
        mScrollViewConstraintLayout = findViewById(R.id.scrollViewConstraintLayout);
        mScrollViewConstraintLayout.setVisibility(View.GONE);

        //Progress bar
        mProgressBar = findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        city = sharedPreferences.getString("cityName", "");
        Log.d(TAG, "onCreate: city" + city);

        // Navigation drawer
        mSettingsNav = findViewById(R.id.settingsTextView);
        mAboutUsNav = findViewById(R.id.aboutUsTextView);

        // Sunset, Sunrise layout
        mSunLayoutTV = findViewById(R.id.sunLayoutTV);

        // Linking the elements in the layout to Java code.
        mCityLabel = findViewById(R.id.locationTV);
        mWeatherImage = findViewById(R.id.weatherSymbolIV);
        mDateTextView = findViewById(R.id.dateTextView);
        mDayTextView = findViewById(R.id.dayTextView);
        mTemperatureLabel = findViewById(R.id.tempTV);
        mCurrentWeatherMainTV = findViewById(R.id.currentWeatherMain);
        mCurrentWeatherDescriptionTV = findViewById(R.id.currentWeatherDescription);
        LinearLayout changeCityButton = findViewById(R.id.locationButtonLayout);

        mMotionLayout = findViewById(R.id.motion_layout);
        mImageButton = findViewById(R.id.img);

        //Weather Forecast layout
        mWeekDay1TV = findViewById(R.id.weekDay1TV);
        mWeekDay2TV = findViewById(R.id.weekDay2TV);
        mWeekDay3TV = findViewById(R.id.weekDay3TV);
        mWeekDay4TV = findViewById(R.id.weekDay4TV);
        mWeekDay5TV = findViewById(R.id.weekDay5TV);

        mWeekDay1IV = findViewById(R.id.weekDay1IV);
        mWeekDay2IV = findViewById(R.id.weekDay2IV);
        mWeekDay3IV = findViewById(R.id.weekDay3IV);
        mWeekDay4IV = findViewById(R.id.weekDay4IV);
        mWeekDay5IV = findViewById(R.id.weekDay5IV);


        mWeekDay1TempTV = findViewById(R.id.weekDay1TempTV);
        mWeekDay2TempTV = findViewById(R.id.weekDay2TempTV);
        mWeekDay3TempTV = findViewById(R.id.weekDay3TempTV);
        mWeekDay4TempTV = findViewById(R.id.weekDay4TempTV);
        mWeekDay5TempTV = findViewById(R.id.weekDay5TempTV);

        //Sunset sunrise layout
        mSunriseTimeTextView = findViewById(R.id.sunriseTimeTextView);
        mSunsetTimeTextView = findViewById(R.id.sunsetTimeTextView);

        //Weather Quote layout
        mWeatherQuoteTV = findViewById(R.id.weatherQuoteTV);

        //Detailed view layout
        mDetailViewLocationTV = findViewById(R.id.detailViewLocationText);
        mDetailViewTempTV =findViewById(R.id.detailViewTempText);
        mWindSpeedTV = findViewById(R.id.windSpeedTextView);
        mHumidityPercentTV = findViewById(R.id.humidityPercentTextView);
        mPressureHPATV = findViewById(R.id.pressureHPATextView);
        mCloudinessTV =findViewById(R.id.cloudinessTextView);
        mUvIndexTV = findViewById(R.id.uvIndexTextView);
        mVisibilityRangeTV = findViewById(R.id.visibilityRangeTextView);


        // Navigation Drawer
        mSettingsNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, Settings.class);
                Log.d(LOGCAT_TAG, "onClick: Settings Button Clicked");
                startActivity(myIntent);
            }
        });
        mAboutUsNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, AboutUs.class);
                Log.d(LOGCAT_TAG, "onClick: AboutUs Button Clicked");
                startActivity(myIntent);
            }
        });


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


        // Image Slider
        sliderView = findViewById(R.id.imageSlider);


        adapter = new SliderAdapterExample(this);
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();

        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                Log.i("GGG", "onIndicatorClicked: " + sliderView.getCurrentPagePosition());
            }
        });

        renewItems();





        // Add an OnClickListener to the changeCityButton here:
        changeCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mMotionLayout.transitionToEnd();
//                mMotionLayout.transitionToStart();

                Intent myIntent = new Intent(MainActivity.this, ChangeCityController.class);
                Log.d(LOGCAT_TAG, "onClick: Change City Button Pressed");
                // Using startActivityForResult since we just get back the city name.
                // Providing an arbitrary request code to check against later.
                startActivityForResult(myIntent, NEW_CITY_CODE);
                mUseLocation = false;
            }
        });

//         getWeatherForCurrentLocation();

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2) {
            city = data.getStringExtra("City");
            Log.d(TAG, "onActivityResult: " + city);
            if (city != null) {
                getWeatherForNewCity(city);
            } else {
                getWeatherForCurrentLocation();
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOGCAT_TAG, "onResume() called");
        Log.d(LOGCAT_TAG, "onResume: ");

        if (mUseLocation) getWeatherForCurrentLocation();
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

    private void letsDoSomeNetworking(RequestParams params) {
        Log.d(LOGCAT_TAG, "Entered letsDoSomeNetworking");

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(WEATHER_URL, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d(LOGCAT_TAG, "Success! JSON: " + response.toString());
                WeatherDataModel weatherData = WeatherDataModel.fromJson(response);
                updateUI(weatherData);
                LOCATION_LATITUDE = weatherData.getLat();
                LOCATION_LONGITUDE = weatherData.getLon();
                getWeatherForCurrentLocation1();
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

    private void getWeatherForCurrentLocation1() {
        Log.d(LOGCAT_TAG, "Getting weather for current ONE CALL API");
        RequestParams params = new RequestParams();
        params.put("lat", LOCATION_LATITUDE);
        params.put("lon", LOCATION_LONGITUDE);
        params.put("exclude", "minutely,hourly");
        params.put("appid", APP_ID);
        letsDoSomeNetworkingForOCAPI(params);
    }

    private void letsDoSomeNetworkingForOCAPI(RequestParams params) {

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(ONE_CALL_API_URL, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d(LOGCAT_TAG, "Success! JSON: " + response.toString());
                WeatherDataModelForOCAPI weatherDataForOCAPI = WeatherDataModelForOCAPI.fromJson(response);
                updateUIForOCAPI(weatherDataForOCAPI);
               mProgressBar.setVisibility(View.GONE);
               mScrollViewConstraintLayout.setVisibility(View.VISIBLE);
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
        mCityLabel.setText(weather.getCity());
        mSunLayoutTV.setText("Sun in " + weather.getCity());
        mTemperatureLabel.setText(weather.getTemperature());

        mCurrentWeatherMainTV.setText(weather.getCurrentWeatherMain());
        String cap = weather.getCurrentWeatherDescription().substring(0, 1).toUpperCase() + weather.getCurrentWeatherDescription().substring(1) + " today";
        mCurrentWeatherDescriptionTV.setText(cap);

        CURRENT_TIMEZONE = Integer.parseInt(weather.getTimeZone());
        CURRENT_TIME =  (  Integer.parseInt(weather.getCurrentDateAndTime()));

//        CURRENT_TIME = Integer.parseInt(weather.getTimeZone()) + (  Integer.parseInt(weather.getCurrentDateAndTime()));

        CurrentTime currentTime = CurrentTime.getCurrentTime( CURRENT_TIME);

        mDayTextView.setText(currentTime.getDay());
        String dateString = currentTime.getMonth() + " " + currentTime.getDate() + ", " + currentTime.getYear()+ " "+ currentTime.getHour() + " PM";
        mDateTextView.setText(dateString);

        int resourceID = getResources().getIdentifier(weather.getIconName(), "drawable", getPackageName());
        mWeatherImage.setImageResource(resourceID);

        mWeekDay1TV.setText(currentTime.getNextDay1().substring(0, 3).toUpperCase());
        mWeekDay2TV.setText(currentTime.getNextDay2().substring(0, 3).toUpperCase());
        mWeekDay3TV.setText(currentTime.getNextDay3().substring(0, 3).toUpperCase());
        mWeekDay4TV.setText(currentTime.getNextDay4().substring(0, 3).toUpperCase());
        mWeekDay5TV.setText(currentTime.getNextDay5().substring(0, 3).toUpperCase());

        mDetailViewLocationTV.setText("Weather today in " + weather.getCity() + ", " + weather.getCountryName());
    }

    private void updateUIForOCAPI(WeatherDataModelForOCAPI weather) {
        Log.d(LOGCAT_TAG, "updateUIForOCAPI: lat " + weather.getLatitude());
        Log.d(LOGCAT_TAG, "updateUIForOCAPI: lon " + weather.getLongitude());

        mWeekDay1TempTV.setText(weather.getM1ForecastTemperature());
        mWeekDay2TempTV.setText(weather.getM2ForecastTemperature());
        mWeekDay3TempTV.setText(weather.getM3ForecastTemperature());
        mWeekDay4TempTV.setText(weather.getM4ForecastTemperature());
        mWeekDay5TempTV.setText(weather.getM5ForecastTemperature());

        CurrentTime currentTimeForSR = CurrentTime.getCurrentTime(  weather.getSunrise());
        mSunriseTimeTextView.setText(currentTimeForSR.getHour() + ":" + currentTimeForSR.getMinutes() + " AM");

        CurrentTime currentTimeForSS = CurrentTime.getCurrentTime(  weather.getSunset());
        mSunsetTimeTextView.setText(currentTimeForSS.getHour() + ":" + currentTimeForSS.getMinutes() + " PM");

        mDetailViewTempTV.setText(weather.getTemperatureFeelsLike());
        mWindSpeedTV.setText(weather.getTemperatureFeelsLike() + " m/sec");
        mHumidityPercentTV.setText(weather.getHumidity() + "%");
        mPressureHPATV.setText(weather.getPressure() + " hPa");
        mCloudinessTV.setText(weather.getClouds()+ "%");
        mUvIndexTV.setText(weather.getUVIndex());
        mVisibilityRangeTV.setText(weather.getVisibility()+"m");

        Glide.with(this).load(IMAGE_LOAD_LINK + weather.getM1ForecastIcon() + "@2x.png").into(mWeekDay1IV);
        Glide.with(this).load(IMAGE_LOAD_LINK + weather.getM2ForecastIcon() + "@2x.png").into(mWeekDay2IV);
        Glide.with(this).load(IMAGE_LOAD_LINK + weather.getM3ForecastIcon() + "@2x.png").into(mWeekDay3IV);
        Glide.with(this).load(IMAGE_LOAD_LINK + weather.getM4ForecastIcon() + "@2x.png").into(mWeekDay4IV);
        Glide.with(this).load(IMAGE_LOAD_LINK + weather.getM5ForecastIcon() + "@2x.png").into(mWeekDay5IV);



//        Picasso.get().load(IMAGE_LOAD_LINK + weather.getM2ForecastIcon() + "@2x.png").into(mWeekDay2IV);
//        Picasso.get().load(IMAGE_LOAD_LINK + weather.getM3ForecastIcon() + "@2x.png").into(mWeekDay3IV);
//        Picasso.get().load(IMAGE_LOAD_LINK + weather.getM4ForecastIcon() + "@2x.png").into(mWeekDay4IV);
//        Picasso.get().load(IMAGE_LOAD_LINK + weather.getM5ForecastIcon() + "@2x.png").into(mWeekDay5IV);

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

    // Image Slider
    public void renewItems() {
        List<SliderItem> sliderItemList = new ArrayList<>();
        //dummy data
        for (int i = 0; i < SLIDER_COUNT; i++) {
            SliderItem sliderItem = new SliderItem();
            if (i % 2 == 0) {
                sliderItem.setImageUrl("https://firebasestorage.googleapis.com/v0/b/weather-feed-1d79c.appspot.com/o/fSlide2.png?alt=media&token=7c0c55f0-d95e-4489-a006-f1686471e212");
            } else {
                sliderItem.setImageUrl("https://firebasestorage.googleapis.com/v0/b/weather-feed-1d79c.appspot.com/o/fSlide1.png?alt=media&token=68241ad2-91e3-447f-965d-cb71c141fdb9");
            }
            sliderItemList.add(sliderItem);
        }
        adapter.renewItems(sliderItemList);
    }

}