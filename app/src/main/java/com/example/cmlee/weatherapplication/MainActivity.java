package com.example.cmlee.weatherapplication;

import android.content.Intent;
import android.database.DataSetObserver;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity
        //Implements the list item listener
        implements AdapterView.OnItemClickListener {

    private ArrayAdapter<String> cityModel = null;
    private List<String> cities = null;

    private List<String> initializeCities() {
        List<String> l = new LinkedList<>();
        l.add("Singapore");
        l.add("Kuala Lumpur");
        l.add("Bangkok");
        l.add("Jakarta");
        l.add("Manila");
        return (l);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create the model and populate it with initial list of cities
        cities = initializeCities();
        cityModel = new ArrayAdapter<>(
                this, //is the current activity
                android.R.layout.simple_list_item_1, //default layout just a string
                cities);

        //Look for our list component add model to it
        ListView lv = (ListView)findViewById(R.id.cityList);
        lv.setAdapter(cityModel);

        //Add a listener to listen to list selection
        lv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //Get the city name from our list
        String cityName = cities.get(i);

        //Create an intent to start WeatherDetailsActivity
        Intent intent = new Intent(this, WeatherDetailsActivity.class);

        //Add cityName to the intent
        intent.putExtra("cityName", cityName);

        //Start the activity
        startActivity(intent);

        //Start activity with the intent
        /*
        Toast.makeText(this, "City name:" + cityName, Toast.LENGTH_LONG)
                .show();
        */

    }

    //Install the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate our menu and add it to the toolbar
        getMenuInflater().inflate(R.menu.menu, menu);
        return (true);
    }

    //Handle the Add button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //If you have more than one menu item
        switch (item.getItemId()) {
            case R.id.mi_add_city:
                Intent intent = new Intent(this, AddCityActivity.class);
                startActivityForResult(intent, 0);
                break;

            default:
        }
        return (true);
    }

    //Receive the result from AddCityAcitivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //If cancel has been pressed, do nothing
        if (resultCode == -1)
            return;

        //Get the new city name using the key
        String cityName = data.getStringExtra("cityName");
        //Add this to the list
        cities.add(cityName);
        //Notify the ListView that the underlying data has changed
        cityModel.notifyDataSetChanged();
    }
}
