package com.example.weatherfeed;

import org.json.JSONException;
import org.json.JSONObject;

public class WeatherDataModelForOCAPI {

    private String mLatitude;
    private String mLongitude;
    private String mTimeZone;
    //Current
    private String mCurrentUTC;
    private String mSunrise;
    private String mSunset;
    private String mTemperature;
    private String mTemperatureFeelsLike;
    private String mHumidity;
    private String mClouds;
    //weather
    private String mWeatherID;
    private String mWeatherMain;
    private String mWeatherDescription;
    private String mWeatherIcon;

    //daily forecast for next five days
    // Day 1.
    private String m1ForecastUTC;
    private String m1ForecastTemperature;
    private String m1ForecastWeatherID;
    private String m1ForecastWeatherMain;
    private String m1ForecastWeatherDescription;
    private String m1ForecastIcon;
    // Day 2.
    private String m2ForecastUTC;
    private String m2ForecastTemperature;
    private String m2ForecastWeatherID;
    private String m2ForecastWeatherMain;
    private String m2ForecastWeatherDescription;
    private String m2ForecastIcon;
    // Day 3.
    private String m3ForecastUTC;
    private String m3ForecastTemperature;
    private String m3ForecastWeatherID;
    private String m3ForecastWeatherMain;
    private String m3ForecastWeatherDescription;
    private String m3ForecastIcon;
    // Day 4.
    private String m4ForecastUTC;
    private String m4ForecastTemperature;
    private String m4ForecastWeatherID;
    private String m4ForecastWeatherMain;
    private String m4ForecastWeatherDescription;
    private String m4ForecastIcon;
    // Day 5.
    private String m5ForecastUTC;
    private String m5ForecastTemperature;
    private String m5ForecastWeatherID;
    private String m5ForecastWeatherMain;
    private String m5ForecastWeatherDescription;
    private String m5ForecastIcon;


    public static WeatherDataModelForOCAPI fromJson(JSONObject jsonObject) {

        try {
            WeatherDataModelForOCAPI weatherData = new WeatherDataModelForOCAPI();

            //Getting the values
            weatherData.mLatitude = jsonObject.getString("lat");
            weatherData.mLongitude = jsonObject.getString("lon");
            weatherData.mTimeZone = jsonObject.getString("timezone");
            //Current data
            weatherData.mCurrentUTC = jsonObject.getJSONObject("current").getString("dt");
            weatherData.mSunrise = jsonObject.getJSONObject("current").getString("sunrise");
            weatherData.mSunset = jsonObject.getJSONObject("current").getString("sunset");
            weatherData.mTemperature = jsonObject.getJSONObject("current").getString("temp");
            weatherData.mTemperatureFeelsLike = jsonObject.getJSONObject("current").getString("feels_like");
            weatherData.mHumidity = jsonObject.getJSONObject("current").getString("humidity");
            weatherData.mClouds = jsonObject.getJSONObject("current").getString("clouds");
            //Weather data
            weatherData.mWeatherID = jsonObject.getJSONObject("current").getJSONArray("weather").getJSONObject(0).getString("id");
            weatherData.mWeatherMain = jsonObject.getJSONObject("current").getJSONArray("weather").getJSONObject(0).getString("main");
            weatherData.mWeatherDescription = jsonObject.getJSONObject("current").getJSONArray("weather").getJSONObject(0).getString("description");
            weatherData.mWeatherIcon = jsonObject.getJSONObject("current").getJSONArray("weather").getJSONObject(0).getString("icon");

            //Daily weather forecast data for Day 1.
            weatherData.m1ForecastUTC = jsonObject.getJSONArray("daily").getJSONObject(0).getString("dt");
            double tempResult = jsonObject.getJSONArray("daily").getJSONObject(0).getJSONObject("temp").getDouble("day") - 273.15;
            int roundedValue = (int) Math.rint(tempResult);
            weatherData.m1ForecastTemperature =  Integer.toString(roundedValue) + "°C";
            weatherData.m1ForecastWeatherID = jsonObject.getJSONArray("daily").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("id");
            weatherData.m1ForecastWeatherMain = jsonObject.getJSONArray("daily").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("main");
            weatherData.m1ForecastWeatherDescription = jsonObject.getJSONArray("daily").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("description");
            weatherData.m1ForecastIcon = jsonObject.getJSONArray("daily").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("icon");

            //Daily weather forecast data for Day 2.
            weatherData.m2ForecastUTC = jsonObject.getJSONArray("daily").getJSONObject(1).getString("dt");
            weatherData.m2ForecastTemperature = jsonObject.getJSONArray("daily").getJSONObject(1).getJSONObject("temp").getString("day");
            weatherData.m2ForecastWeatherID = jsonObject.getJSONArray("daily").getJSONObject(1).getJSONArray("weather").getJSONObject(0).getString("id");
            weatherData.m2ForecastWeatherMain = jsonObject.getJSONArray("daily").getJSONObject(1).getJSONArray("weather").getJSONObject(0).getString("main");
            weatherData.m2ForecastWeatherDescription = jsonObject.getJSONArray("daily").getJSONObject(1).getJSONArray("weather").getJSONObject(0).getString("description");
            weatherData.m2ForecastIcon = jsonObject.getJSONArray("daily").getJSONObject(1).getJSONArray("weather").getJSONObject(0).getString("icon");

            //Daily weather forecast data for Day 3.
            weatherData.m3ForecastUTC = jsonObject.getJSONArray("daily").getJSONObject(2).getString("dt");
            weatherData.m3ForecastTemperature = jsonObject.getJSONArray("daily").getJSONObject(2).getJSONObject("temp").getString("day");
            weatherData.m3ForecastWeatherID = jsonObject.getJSONArray("daily").getJSONObject(2).getJSONArray("weather").getJSONObject(0).getString("id");
            weatherData.m3ForecastWeatherMain = jsonObject.getJSONArray("daily").getJSONObject(2).getJSONArray("weather").getJSONObject(0).getString("main");
            weatherData.m3ForecastWeatherDescription = jsonObject.getJSONArray("daily").getJSONObject(2).getJSONArray("weather").getJSONObject(0).getString("description");
            weatherData.m3ForecastIcon = jsonObject.getJSONArray("daily").getJSONObject(2).getJSONArray("weather").getJSONObject(0).getString("icon");

            //Daily weather forecast data for Day 4.
            weatherData.m4ForecastUTC = jsonObject.getJSONArray("daily").getJSONObject(3).getString("dt");
            weatherData.m4ForecastTemperature = jsonObject.getJSONArray("daily").getJSONObject(3).getJSONObject("temp").getString("day");
            weatherData.m4ForecastWeatherID = jsonObject.getJSONArray("daily").getJSONObject(3).getJSONArray("weather").getJSONObject(0).getString("id");
            weatherData.m4ForecastWeatherMain = jsonObject.getJSONArray("daily").getJSONObject(3).getJSONArray("weather").getJSONObject(0).getString("main");
            weatherData.m4ForecastWeatherDescription = jsonObject.getJSONArray("daily").getJSONObject(3).getJSONArray("weather").getJSONObject(0).getString("description");
            weatherData.m4ForecastIcon = jsonObject.getJSONArray("daily").getJSONObject(3).getJSONArray("weather").getJSONObject(0).getString("icon");

            //Daily weather forecast data for Day 5.
            weatherData.m5ForecastUTC = jsonObject.getJSONArray("daily").getJSONObject(4).getString("dt");
            weatherData.m5ForecastTemperature = jsonObject.getJSONArray("daily").getJSONObject(4).getJSONObject("temp").getString("day");
            weatherData.m5ForecastWeatherID = jsonObject.getJSONArray("daily").getJSONObject(4).getJSONArray("weather").getJSONObject(0).getString("id");
            weatherData.m5ForecastWeatherMain = jsonObject.getJSONArray("daily").getJSONObject(4).getJSONArray("weather").getJSONObject(0).getString("main");
            weatherData.m5ForecastWeatherDescription = jsonObject.getJSONArray("daily").getJSONObject(4).getJSONArray("weather").getJSONObject(0).getString("description");
            weatherData.m5ForecastIcon = jsonObject.getJSONArray("daily").getJSONObject(4).getJSONArray("weather").getJSONObject(0).getString("icon");


            return weatherData;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String updateWeatherIcon(int condition) {

        if (condition >= 0 && condition < 300) {
            return "tstorm1";
        } else if (condition >= 300 && condition < 500) {
            return "light_rain";
        } else if (condition >= 500 && condition < 600) {
            return "shower3";
        } else if (condition >= 600 && condition <= 700) {
            return "snow4";
        } else if (condition >= 701 && condition <= 771) {
            return "fog";
        } else if (condition >= 772 && condition < 800) {
            return "tstorm3";
        } else if (condition == 800) {
            return "sunny";
        } else if (condition >= 801 && condition <= 804) {
            return "cloudy2";
        } else if (condition >= 900 && condition <= 902) {
            return "tstorm3";
        } else if (condition == 903) {
            return "snow5";
        } else if (condition == 904) {
            return "sunny";
        } else if (condition >= 905 && condition <= 1000) {
            return "tstorm3";
        }

        return "dunno";
    }


    public String getLatitude() {
        return mLatitude;
    }

    public String getLongitude() {
        return mLongitude;
    }

    public String getCurrentUTC() {
        return mCurrentUTC;
    }

    public String getSunrise() {
        return mSunrise;
    }

    public String getSunset() {
        return mSunset;
    }

    public String getTemperatureFeelsLike() {
        return mTemperatureFeelsLike;
    }

    public String getHumidity() {
        return mHumidity;
    }

    public String getClouds() {
        return mClouds;
    }

    public String getWeatherID() {
        return mWeatherID;
    }

    public String getWeatherMain() {
        return mWeatherMain;
    }

    public String getWeatherDescription() {
        return mWeatherDescription;
    }

    public String getWeatherIcon() {
        return mWeatherIcon;
    }

    public String getM1ForecastUTC() {
        return m1ForecastUTC;
    }

    public String getM1ForecastTemperature() {
        return m1ForecastTemperature;
    }

    public String getM1ForecastWeatherID() {
        return m1ForecastWeatherID;
    }

    public String getM1ForecastWeatherMain() {
        return m1ForecastWeatherMain;
    }

    public String getM1ForecastWeatherDescription() {
        return m1ForecastWeatherDescription;
    }

    public String getM1ForecastIcon() {
        return m1ForecastIcon;
    }

    public String getM2ForecastUTC() {
        return m2ForecastUTC;
    }

    public String getM2ForecastTemperature() {
        return m2ForecastTemperature;
    }

    public String getM2ForecastWeatherID() {
        return m2ForecastWeatherID;
    }

    public String getM2ForecastWeatherMain() {
        return m2ForecastWeatherMain;
    }

    public String getM2ForecastWeatherDescription() {
        return m2ForecastWeatherDescription;
    }

    public String getM2ForecastIcon() {
        return m2ForecastIcon;
    }

    public String getM3ForecastUTC() {
        return m3ForecastUTC;
    }

    public String getM3ForecastTemperature() {
        return m3ForecastTemperature;
    }

    public String getM3ForecastWeatherID() {
        return m3ForecastWeatherID;
    }

    public String getM3ForecastWeatherMain() {
        return m3ForecastWeatherMain;
    }

    public String getM3ForecastWeatherDescription() {
        return m3ForecastWeatherDescription;
    }

    public String getM3ForecastIcon() {
        return m3ForecastIcon;
    }

    public String getM4ForecastUTC() {
        return m4ForecastUTC;
    }

    public String getM4ForecastTemperature() {
        return m4ForecastTemperature;
    }

    public String getM4ForecastWeatherID() {
        return m4ForecastWeatherID;
    }

    public String getM4ForecastWeatherMain() {
        return m4ForecastWeatherMain;
    }

    public String getM4ForecastWeatherDescription() {
        return m4ForecastWeatherDescription;
    }

    public String getM4ForecastIcon() {
        return m4ForecastIcon;
    }

    public String getM5ForecastUTC() {
        return m5ForecastUTC;
    }

    public String getM5ForecastTemperature() {
        return m5ForecastTemperature;
    }

    public String getM5ForecastWeatherID() {
        return m5ForecastWeatherID;
    }

    public String getM5ForecastWeatherMain() {
        return m5ForecastWeatherMain;
    }

    public String getM5ForecastWeatherDescription() {
        return m5ForecastWeatherDescription;
    }

    public String getM5ForecastIcon() {
        return m5ForecastIcon;
    }

    public String getTimeZone() {
        return mTimeZone;
    }


}
