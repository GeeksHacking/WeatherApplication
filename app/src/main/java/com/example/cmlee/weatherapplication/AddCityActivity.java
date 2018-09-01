package com.example.cmlee.weatherapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddCityActivity extends AppCompatActivity
        implements View.OnClickListener { //Need to implement the listener interface

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);

        //Find the cancel button
        Button button = (Button)findViewById(R.id.btn_cancel);
        //Add a click listener to it
        button.setOnClickListener(this);

        //Find the add button
        button = (Button)findViewById(R.id.btn_add);
        button.setOnClickListener(this);
    }

    //This will be called when button are pressed
    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btn_cancel) {
            //Set -1 to indicate that the user has cancelled
            setResult(-1);
            //Finish the activity - go back to the main activity
            finish();
            return;
        }

        //Add button
        //Find the edit text / text field
        EditText etCity = (EditText)findViewById(R.id.cityName);
        //Get the city name that the user have entered
        String cityName = etCity.getText().toString();

        //Create an intent - data bundle
        Intent intent = new Intent();
        //Add the city name to it
        intent.putExtra("cityName", cityName);
        //Set the result code and pass the city name back
        setResult(1, intent);

        finish();
    }
}
