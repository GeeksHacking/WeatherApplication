package com.example.cmlee.weatherapplication;

import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class WeatherDetailsActivity extends AppCompatActivity {

    private static final String APPID = "b73a8ebfdd91898e037eee4a25386c4d";

    private List<String> getWeather(String cityName) throws Exception {

        //Construct the request URL
        final String weatherURL = "https://api.openweathermap.org/data/2.5/weather?q="
                + cityName + "&appid=" + APPID;

        //Create a URL object to make the call
        URL url = new URL(weatherURL);

        //Open the connection
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();

        //This will hold the result from openweathermap
        StringBuilder result = new StringBuilder();

        //Get the input stream from our connection
        try (InputStream is = conn.getInputStream()) {
            //Create a reader
            InputStreamReader reader = new InputStreamReader(is);
            //Create a 4K buffer to store data
            char[] buffer = new char[1024 * 4];
            int count = 0;
            //Accumulate the data as it comes in from the network
            //-1 is EOF
            while ((count = reader.read(buffer, 0, buffer.length)) != -1) {
                result.append(buffer, 0, count);
            }
        }

        List<String> weatherDetails = new LinkedList<>();

        //Parse the string into JSON object
        JSONObject weatherData = new JSONObject(result.toString());
        //Get the weather attribute from the result
        JSONArray readings = weatherData.getJSONArray("weather");

        //Loop thru the weather array
        for (int i = 0; i < readings.length(); i++) {
            JSONObject jo = readings.getJSONObject(i);
            weatherDetails.add(jo.getString("main") +
                    " - " + jo.getString("description"));
        }

        return (weatherDetails);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_details);

        //Setting the policy
        if (Build.VERSION.SDK_INT > 9) {
            //Create a policy
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //Retrieve the city name from intent
        String cityName = getIntent().getStringExtra("cityName");

        //Get the label and set the display
        TextView tvWeatherLabel = (TextView)findViewById(R.id.weatherLabel);
        tvWeatherLabel.setText("Weather for " + cityName);

        try {
            //Get the weather
            List<String> weather = getWeather(cityName);
            //Create a string builder to hold our data
            StringBuilder sb = new StringBuilder();
            //Every reading put it in a new line
            for (String s: weather) {
                sb.append(s + "\n");
            }

            //Get the weather details
            EditText weatherDetails = (EditText)findViewById(R.id.weatherDetails);
            //Do not allow edits
            weatherDetails.setEnabled(false);
            //Display the result
            weatherDetails.setText(sb.toString());

        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(this, "Exception:" + ex.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }
    }
}
