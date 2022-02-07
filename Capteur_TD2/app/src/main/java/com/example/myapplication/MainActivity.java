package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView t = findViewById(R.id.textView);
        Spinner dropdown = findViewById(R.id.spinner1);

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

        ArrayList<String> sensorSearch = new ArrayList<>();

        sensorSearch.add("accelerometer");
        sensorSearch.add("magnetometer");
        sensorSearch.add("orientation");
        sensorSearch.add("gyroscope");
        sensorSearch.add("light");
        sensorSearch.add("accelerometer");
        sensorSearch.add("gravity");
        sensorSearch.add("rotation");
        sensorSearch.add("pedometer");
        sensorSearch.add("motion");
        sensorSearch.add("touch");

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, sensorSearch);
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                t.setText("");
                for(Sensor s: sensors){
                    if(s.getName().toLowerCase(Locale.ROOT).contains(adapter.getItem(i))){
                        t.setText(t.getText()+s.getName().toLowerCase(Locale.ROOT)+"\n");
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }
}